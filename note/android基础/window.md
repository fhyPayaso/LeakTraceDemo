### Window 


Window 有三种类型，分别是应用 Window、子 Window 和系统 Window。

+ 应用类 Window 对应一个 Acitivity。
+ 子 Window 不能单独存在，需要依附在特定的父 Window 中，比如常见的一些 Dialog 就是一个子 Window。
+ 系统 Window是需要声明权限才能创建的 Window，比如 Toast 和系统状态栏都是系统 Window。

> Window 是分层的，每个 Window 都有对应的 z-ordered，层级大的会覆盖在层级小的 Window 上面，这和 HTML 中的 z-index 概念是完全一致的。在三种 Window 中，应用 Window 层级范围是 1~99，子 Window 层级范围是 1000~1999，系统 Window 层级范围是 2000~2999