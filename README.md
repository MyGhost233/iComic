# ComicParse:i漫
### 简介
这是一个第三方的漫画阅读APP.所有数据均来自各APP,致谢列表在下面.

本项目仅用于研究分析学习使用,他人盗取代码二次修改打包引起的版权问题与各项后果与作者本人(秋城落叶)无关.

许可证使用GPL V3授权,禁止利用本App进行商业活动.

### 下载最新编译版
[最新每日编译版 - 2019.3.25](ComicX-release.apk)

### 主要开发人员
QiuChenly(秋城落叶)

### 计划的功能状态
| 序号 | 功能 | 状态 |
| ------ | ------ | ------ |
| 1 | 加载并阅读漫画 | √ |
| 2 | 缓存漫画数据 | × |
| 3 | 适配更多网站API | 正在适配 |
| 4 | 记住阅读的进度 | √ |
| 5 | 自动保存看过的漫画 | √ |

### 开发日志
2019-3-25:
```
内存泄露没救了,这几天开始自闭.

1.未修复任何问题.
```
2019-3-24:
```
增加了一些新功能,修改了界面.

1.增加了BrowserView界面,支持点击加载动漫之家的公告显示.
2.ComicDetails类加入了隐式Intent传递数据,来加载漫画信息.
3.ComicApi接口类加入了获取漫画详情的接口方法getComic来获取到漫画的详情.
4.修改BasicInfo类名为ComicBasicInfo,并针对动漫之家的数据源做了针对性适配.
5.删除了ComicBasicInfo与ComicList类的getInstance()方法,改为setUI(Type)方法来设置Fragment数据,试图解决引用内存泄漏.
6.ComicListViewModel类加入了getDMZJComicList(obj_id: String)方法,来加载漫画数据.
7.ComicDetails类为动漫之家做了多数据源加载适配.
8.ComicPageAda增加了setSourceType方法,来一次性设置数据源来源,并将ItemData设置为String来动态处理数据源数据.
9.ComicPageAda类中的onViewShow针对多数据源做了适配.
10.修改enum类ComicSourcceType为ComicSourceType,修改一处单词拼写错误.
11.删除了漫画详情页的两个用不到的按钮与删除了'字母索引'和'评分'视图.
12.主分支改为SingleSource分支.master分支暂时不作处理.
13.RecentlyPagerAdapter类中的getInstance方法改为通过Fragment的引用名称来获取对应的Fragment实例.
14.mTopViewBanner修改为ViewBanner类.
15.RecommendViewModel类删除了几个冗余字段代码.
16.搜索结果页的代码微调,未造成功能性改变.
17.getDMZJRecommend方法,修改为当请求动漫之家的推荐数据请求成功后才会去加载动漫之家的漫画类别请求.
18.修改数据填充逻辑.当哔咔数据比动漫之家的数据先获取到的话,则当动漫之家的数据加载出来的时候自动让哔咔数据跑到底部.
19.支持阅读部分动漫之家漫画数据,支持加载部分动漫之家漫画数据.
20.删除了ComicReadingAdapter类中的setBikaMode方法.
21.MyAppGlideModule类中修复了动漫之家检查图片数据来源导致无法加载图片的bug.
22.支持显示阅读时漫画章节名称.
23.ReadViewModel类中增加了getDMZJImage方法.
24.支持动漫之家 - 最近更新 板块的漫画的阅读.
25.支持动漫之家 - 漫画详情页的番外与连载目录一起加载.
```
2019-3-23:
```
增加了一些新功能与UI的更改.

1.搜索结果界面的图片改为了半径为8的圆角.
2.新增了动漫之家漫画类别显示.
3.修复了Banner栏不会自动滚动的bug.(禁止用户手动滑动Banner.)
4.优化部分代码懒加载技术,节省加载时间.
```
2019-3-22:
```
1.增加了几个新的有趣API接口.
2.修正因代码OkHttp3 DNS代码初始化问题导致第一次打开APP无法登录BIKA的BUG.
3.这两天在划水.
```
2019-3-20:
```
1.修改了MainActivity为SingleTask.
2.集成Fabric分析App崩溃日志.
3.删掉了App多余的一个图像高斯模糊处理库.
4.预集成动漫之家(dmzj.com)API.
5.加入DNS解析,分别判断各个类型的DNS解析.
```
### 致谢第三方API
1.dmzj.com(APP协议,暂无算法加密,只有一个SSL认证,官方使用Async-Http库.)

2.哔咔漫画(APP协议,RSA Sign算法加密+SO层算法逆向,官方使用OkHttp3 双向SSL认证.)