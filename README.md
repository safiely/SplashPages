# SplashPages
引导页面的设计
ViewPage类：
继承了ViewGroup,
类似ListView的用户，为它设置适配器，自定义的适配器中传入你的引导界面View 。
用SharePreference文件来记录是否为第一次登录，决定是否进入引导页或登录页
