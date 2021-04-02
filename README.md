# SkillExchange
模仿国外的一款软件（taskmall）做的“技能交换”平台


#android 3.2（作为客户端）
#mysql 5.3（Navicat可视化） 
#eclipse IDE 4.15.0（作为服务器）
#Tomcat 8.0
#项目中有用到jieba分词，原作者地址：https://github.com/huaban/jieba-analysis
#项目中关于匹配算法（tf-idf匹配）由于暂时没有找到和本项目相关度较高的语料库，匹配结果可能会出现错误，目前还没有完成，功能点即在首页的搜索框，思路是利用结巴分词将文本框和listview中任务标题和描述的关键词利用jieba分词+tf-idf要提取出来，最后用余弦相似度比较两者的匹配程度，优先展示匹配度高的内容。
#注意： 要修改url地址，改为自己联网的本机ip地址
       修改服务器的语料库地址，本项目在C:\Users\Lenovo\Desktop\cankao\Reduced-fenci目录下，相关代码修改在impl包的JieBaImpl.java下
       有些功能由于时间原因只是做了界面上的展示，后台功能还需完善
       头像截取图片的时候不要太大，能小尽量小，因为用Base64传输的
       学校android实习项目，代码编写的可能不太规范，根据需要自行取舍
	

