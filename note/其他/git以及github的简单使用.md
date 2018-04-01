# git以及github的简单使用

标签：git使用

---

## 基本操作

 - 在当前文件夹初始化本地仓库
 
        $ git init

 - 添加所有改动到缓冲区
 
        $ git add .

 - 将缓冲区的文件添加到本地仓库
 
        $ git commit

 - 查看仓库状态（未add显红，未commit显绿）
 
        $ git status

 - 查看commit记录
 
        $ git log

---
## 分支管理

 - 查看当前本地所有分支以及当前分支
 
        $ git branch 

 - 切换到指定分支
 
        $ git checkout <分支名>

 - 建立新的本地分支

        $ git checkout -b <分支名>
        
 - 拉取指定的远程分支到指定的本地分支
 
        $ git pull origin <远程分支名>:<本地分支名> 

 - 将指定分支与当前分支合并（如果出现冲突则手动决解，注意add和commit）
 
        $ git merge <分支名>
        

---
## remote使用

- 列出已经存在的远程分支

        $ git remote

- 删除oringin

        $ git remote rm oringin

- 给oringin添加远程仓库地址

        $ git remote add origin <url>
        
- 增加oringin仓库地址

        $ git remote set-url --add origin <url>

- 删除oringin仓库地址

        $ git remote set-url --delete origin <url>
- 查看当前所有远程仓库的地址

        $ git remote -v |--verbose
        
- 第一次pull失败的情况,添加

        $ --allow-unrelated-histories
        

 
 

