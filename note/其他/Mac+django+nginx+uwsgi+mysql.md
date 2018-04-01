# Linux+django+nginx+uwsgi+mysql部署


## 1.mac终端登录服务器ubuntu系统

	ssh ubuntu@123.206.21.17
		
出现了
	
	Permission denied (publickey).
		
		
这是由于没有将公钥`( publickey )`  添加到本地 ssh 环境造成的，或者是由于多日未 进行`ssh` 登录操作，本地 `publickey` 失效造成的。添加一下即可。
	
	ssh-add ~/.ssh/ubuntu

再次登录即可成功。


## 2. 装python环境


+ #### 更新软件列表和本地软件

		sudo apt-get update //列表
		sudo apt-get upgrade //本地


+ #### 安装多版本python

	+ ubuntu自带python2.7版本
		
			python --version
			
	+ 安装python3

			sudo apt-get install python3
				
	+ 为python安装pip3

			sudo apt install python3-pip
				
	+ 更新一下pip版本

			pip3 install --upgrade pip
				
		但是这样操作你会发现pip3并没升级，还是8.1.1版本
			
	+ 用管理员权限更新即可

			sudo -H pip3 install --upgrade pip
				
	+ 查看当前pip3版本，应该是升级到9.0了

			pip3 --version
	
## 3.配置虚拟环境

+ #### 安装虚拟环境

		sudo pip3 install virtualenv
		
	`virtualenv`是虚拟环境安装包，创建虚拟环境可以更好地控制包的版本，包的版本不会因为2.1中的升级操作而升级，保证了项目的稳定性，不同虚拟环境之间的运行环境相互独立，互不干扰。
	
+ #### 管理我们的虚拟环境

		sudo pip install virtualenvwrapper
		
	`virtualenvwrapper`是`virtualenv`的扩展管理包，可以将所有的虚拟环境整合在一个目录下，使用前需要先进行以下配置
	
	+ 创建虚拟环境管理目录

			mkdir ~/.virtualenvs

	+ 打开.bashrc

			sudo vi ~/.bashrc
			
	+ 在末尾添加以下内容
	
			export VIRTUALENVWRAPPER_PYTHON=/usr/bin/python3 # 这句是为了防止环境变量$PATH中已有其它环境的python
			export WORKON_HOME=$HOME/.virtualenvs    # 放所有虚拟环境的地方
			source /usr/local/bin/virtualenvwrapper.sh
			
	+ 启用配置文件

			source ~/.bashrc
			
		这里有个小坑，服务器长时间不操作会中断连接，报错
		
			packet_write_wait: Connection to 123.206.21.17 port 22: Broken pipe
			
		重新登录即可。
			
			
+ #### 创建虚拟环境

		mkvirtualenv env_mysite
		
	创建虚拟环境需要联网,创建完虚拟环境之后会自动进入虚拟环境，可以通过命令行前括号判断是否在虚拟环境内。
	
	![](http://p0y1qzu73.bkt.clouddn.com/18-3-23/61204845.jpg)
	
	常用操作
	
		# 创建虚拟环境
		mkvirtualenv <name>
		# 列出所有虚拟环境
		workon TAB*2
		# 进入虚拟环境
		workon <name>
		# 退出虚拟环境
		deactivate
		# 删除虚拟环境
		rmvirtualenv <name>

+ 在虚拟环境中安装django包

		pip install django


+ 这里安装一个tree包，可以清楚的看见文件接结构

		sudo apt-get install tree
		
	常用方法
	
		# 查看当前目录下所有文件结构
		tree 
		# 只查看当前第一级的目录和文件
		tree -L 1


## 4.用PyCharm将项目上传到服务器（本地）

+ 导出本机虚拟环境中的包

		sudo pip freeze > plist.txt
		
+ 利用PyCharm将代码上传到服务器


	![](http://p0y1qzu73.bkt.clouddn.com/18-3-23/44651550.jpg)
	配置好连接域名和ssh
	![](http://p0y1qzu73.bkt.clouddn.com/18-3-23/28161423.jpg)
	点击测试连接，如果没问题会有成功提示
	![](http://p0y1qzu73.bkt.clouddn.com/18-3-23/85092329.jpg)
	配置你本地上传路径和服务器上项目的路径
	![](http://p0y1qzu73.bkt.clouddn.com/18-3-23/59100245.jpg)
	选择你要上传的部分，右键上传即可
	![](http://p0y1qzu73.bkt.clouddn.com/18-3-23/55882304.jpg)
	再连接你的服务器，就会发现代码已经上传成功了。然后进入虚拟环境，进入项目目录，将之前导出的包安装。
	
		pip install -r plist.txt
	
	
+ 配置项目`settings.py	`，修改以下一项

		ALLOWED_HOSTS = ['*', ]

+ 在项目根目录文件夹下，在终端内（确保已进入虚拟环境）

		python manage.py runserver 0:8000
	
	访问8000端口检查django在服务器上是否能运行,成功说明django项目正常
	

## 5.uWSGI安装及配置

+ 安装Python开发版本（因为安装uWSGI过程中需要编译）

		sudo apt-get install python-dev
		
+ 安装gcc（因为安装uWSGI过程中需要C编译器）

		sudo apt-get install gcc
		
+ 安装uWSGI

		sudo pip install uwsgi
		
	使用`uwsgi --version`命令查看版本号，确认已正确安装
	

## 6.配置uWSGI	

+ #### 打通uWSGI和Python

	+ 在项目根目录创建test.py文件，内容如下

			def application(env, start_response):
		    start_response('200 OK', [('Content-Type','text/html')])
		    return [b"Hello World"]  # python3
		    
	+ 运行uWSGI（表示使用http协议，并使用8000端口，加载指定文件test.py）

			uwsgi --http :8000 --wsgi-file test.py

	+ 访问8000端口，若显示’Hello World’则表示运行正常，说明以下三个环节是相通的

			web client <-> uWSGI <-> Python
			
			
+ #### 打通uWSGI和Django

	+ 在项目根目录创建文件uwsgi.ini，并写入以下内容
	
			[uwsgi]
			# 使用nginx连接时使用
			# socket = 0:8001
			# 直接做web服务器使用
			http = 0:8080
			# 项目目录
			chdir = /home/ubuntu/projectname
			# 项目中wsgi.py文件的目录
			wsgi-file = /home/ubuntu/projectname/projectname/wsgi.py
			# 主进程
			master = true
			# 多进程&多线程
			processes = 6
			threads = 2
			# .sock文件目录需与Nginx文件内的配置相同
			# socket = /home/python/Desktop/project_test/my_sock.sock
			# chmod-socket = 666
			# 以守护进程的方式启动
			vacuum = true
			# 存储pid进程
			pidfile=uwsgi.pid
			# 存储log日志
			daemonize=uwsgi.logv
	
	+ 启动uWSGI服务
	
			uwsgi --ini uwsgi.ini
			
	+ 终端显示以下内容即代表开启成功
	
			[uWSGI] getting INI configuration from my_uwsgi.ini
			
			
	+ 访问8000端口，发现文字可以显示，图片不能显示，这是正常现象
	
		![](http://p0y1qzu73.bkt.clouddn.com/18-3-24/88907634.jpg)
		
	+ 以上步骤说明以下三个环节是相通的
	
			web client <-> uWSGI <-> Django
	
	+ 停止uWSGI服务
	
			uwsgi --stop uwsgi.pid


## 7.配置nginx

+ #### 安装

		sudo apt-get install nginx

	Nginx安装成功后，系统会自动开启 Nginx 服务，默认使用80端口，打开浏览器并输入你服务器的IP地址，就可以看到 nginx 的测试页面.
	
	![](http://p0y1qzu73.bkt.clouddn.com/18-3-24/57137138.jpg)
	
	说明以下两个环节是相通的
	
		web client <-> the web server（Nginx）


+ #### 配置ngxin

	+ 将`/etc/nginx/`目录下的`uwsgi_params`复制到项目文件夹，对此文件不做任何改动

			cp /etc/nginx/uwsgi_params .

	+ 在项目根目录创建文件`my_nginx.conf`，并写入以下内容

			upstream django {
		    server    127.0.0.1:8001;
		    # server      unix://home/python/Desktop/project_test/my_sock.sock;
			}
			server {
			    listen      8000;  # 端口号
			    server_name 127.0.0.1;  # 服务器 ip 或是域名(具体)
			    charset     utf-8;  # 字符集
			    # 最大上传限制
			    # client_max_body_size 75M;
			    location /media  {
			        alias /home/ubuntu/mysite/media_common;  # 媒体文件所在文件夹
			    }
			    location /static {
			        alias /home/ubuntu/mysite/static_common;  # 静态文件所在文件夹
			    }
			    # 将所有非媒体请求转到Django服务器上
			    location / {
			        uwsgi_pass      django;  # 最上方已定义
			        # 将所有参数都转到uwsgi下
			        include         /home/ubuntu/mysite/uwsgi_params; # uwsgi_params的路径
			    }
			}

		这个配置文件表示将静态文件和媒体文件由Nginx处理，而其它的请求转入uWSGI处理
		
		
	+ 与Nginx配置目录建立软链接

			sudo ln -s /home/ubuntu/mysite/my_nginx.conf /etc/nginx/sites-enabled/
			
	+ 这里注意把nginx的默认配置删掉

			rm /etc/nginx/sites-enabled/sites-available/defult
			
	+ 创建静态文件与媒体文件存放目录

			sudo mkdir static_common
			sudo mkdir media_common
			
	+ 如果出现权限问题，将权限改下就好

			chmod 777 static_common
			chmod 777 media_common
			
	+ 修改项目`setting.py`,在最后添加

			STATIC_ROOT = os.path.join(BASE_DIR, 'static_common')
			MEDIA_ROOT = os.path.join(BASE_DIR, 'media_common')
			
	+ 进入项目根目录，执行静态文件迁移命令

			python3 manage.py collectstatic
			
	
## 8.Nginx+uWSGI+Django

+ #### 测试`Nginx`

	+ 重启nginx服务

			sudo /etc/init.d/nginx restart
			
	+ 在浏览器内输入`<YOUR_SERVER_IP>:8000/static/admin/css/base.css`，检查是否能正常显示这个css文件

		![](http://p0y1qzu73.bkt.clouddn.com/18-3-24/47870898.jpg)
		
	+ 将一张测试图片`test.jpg`放入`media_common`文件夹中，在浏览器中输入`<YOUR_SERVER_IP>:8000/media/test.jpg`，如果出现403则将图片的权限改为666，成功显示图片

		![](http://p0y1qzu73.bkt.clouddn.com/18-3-24/63838589.jpg)
		
		
+ #### 测试uWSGI

	在根目录运行之前的`test.py`,如果访问`<YOUR_SERVBER_IP>`显示hello world,说明uWSGI正常
				
		uwsgi --socket :8001 --wsgi-file test.py
		
+ #### 用`UNIX socket`取代`TCP port`


	+ 修改`my_nginx.conf`，最终版如下：


			upstream django {
		    # server    127.0.0.1:8001;
		    server      unix://home/ubuntu/mysite/my_sock.sock;
			}
			
			server {
			    listen      8000;  # 端口号
			    server_name 127.0.0.1;  # 服务器 ip 或是域名(具体)
			    charset     utf-8;  # 字符集
			    # 最大上传限制
			    # client_max_body_size 75M;
			    location /media  {
			        alias /home/ubuntu/mysite/media_common;  # 媒体文件所在文件夹
			    }
			    location /static {
			        alias /home/ubuntu/mysite/static_common;  # 静态文件所在文件夹
			    }
			    # 将所有非媒体请求转到Django服务器上
			    location / {
			        uwsgi_pass      django;  # 最上方已定义
			        # 将所有参数都转到uwsgi下
			        include         /home/ubuntu/mysite/uwsgi_params; # uwsgi_params的路径
			    }
			}
			
			
	+ 修改`my_uwsgi.ini`，最终版如下

			[uwsgi]
			# 使用nginx连接时使用
			# socket = 0:8001
			# 直接做web服务器使用
			# http = 0:8080
			# 项目目录
			chdir = /home/ubuntu/projectname
			# 项目中wsgi.py文件的目录
			wsgi-file = /home/ubuntu/projectname/projectname/wsgi.py
			# 主进程
			master = true
			# 多进程&多线程
			processes = 6
			threads = 2
			# .sock文件目录需与Nginx文件内的配置相同
			socket = /home/home/ubuntu/mysite/my_sock.sock
			chmod-socket = 666
			# 以守护进程的方式启动
			vacuum = true
			# 存储pid进程
			pidfile=uwsgi.pid
			# 存储log日志
			daemonize=uwsgi.logv
			
			
	+ 重启Nginx和uWSGI

			sudo /etc/init.d/nginx restart
			uwsgi --stop uwsgi.pid
			uwsgi --ini my_uwsgi.ini
			
	+ 打开浏览器，地址栏输入网址`<YOUR_SERVER_IP>:8000`，查看图片和文字是否显示正常



	+ 此时以下环节已全部打通

			web client <-> web server(nginx) <-> the socket <-> uwsgi <-> Django
			
	+ 服务器开机自启

		修改`/etc/rc.local`，添加以下内容到`exit 0`之前
		
			/usr/local/bin/uwsgi --ini /home/ubuntu/uwsgi.ini



## 9.配置MySql


+ #### 初始化

	+ 安装mysql，安装过程中会让你设置密码
	
			sudo apt-get install mysql-server	
			
	+ 进入mysql
	
			mysql -u root -p
			
			
		出现下图说明进入成功
		
		![](http://p0y1qzu73.bkt.clouddn.com/18-3-24/3056630.jpg)
		
	+ 展示数据库内容
	
			show database;
		
		![](http://p0y1qzu73.bkt.clouddn.com/18-3-24/69529505.jpg)
		

+ #### 配置mysql能被远程访问





	
## 其他

+ 新建文件

		vim test.py
		
+ 重命名文件

		mv oldname newname
		
+ vim常用操作

		dd # 删除一行
		u # 版本回退