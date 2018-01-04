# Sign
人脸识别签到系统

## 1、概述
该系统核心是调用百度开发者中心提供的人脸识别免费API，所以在使用时要保持网络畅通，并且具有授权到期自动获取授权的功能，详细API说明请参加百度开发者中心。
该系统目前仅供学习和娱乐使用，尚且还有很多不完善的地方，欢迎指出。
由于百度免费API的并发数限制，所以该系统可能在多人使用的情况下出现一些错误，如果您使用频率较高，请联系最后的联系方式，我可以为您配置您自己专属的百度账号，免受多用户冲突的干扰。

## 2、基本逻辑
人脸识别签到的基本逻辑，首先，需要把要进行签到的人的照片全部进行注册，注册到一个组中，然后每签到一个人，将该人的照片与这个组中注册的照片进行识别比对，比对成功的时候，即签到成功，并做对应的记录。

## 3、使用方法
### 1、
首先，使用者的电脑上要有java编译和运行的环境，如果没有请自行安装JDK和JRE，并调试成功，具体如何进行环境搭建，请自行百度。</br>
### 2、
使用者先将sign-0.0.1-SNAPSHOT-publish.tgz包进行解压，我们会得到一个resource文件夹，一个config.xml配置文件和一个sign-0.0.1-SNAPSHOT-build.jar程序包。我们要先使用文本编辑config.xml文件进行必要的参数配置，将图片集参数配置成需要注册图片的文件夹全路径，在这里提示一下，最好将要注册图片进行重命名，命名为真实图片对应人的姓名拼音（百度不支持中文参数，我也是醉了），如果不进行重命名，在后面进行的步骤中，你可能并不会知道，究竟是谁进行了签到。然后配置组id，这个组id就是基本逻辑中所提到的那个组，比如你可以命名为class1，class2这样的名称，还是不建议使用中文。我们创建了组之后里面的图片都是永久保留在远端百度服务器上，所以当你以同一个组名注册过一次图片之后，下一次系统运行识别到该组存在的时候，就不会重新注册了。这里强调一点，就是配置文件的图片集和组id一定要一一对应，如果不对应，会出现一些识别不到或者最后输出结果的未知错误，请一定要注意，这也更强调了config.xml配置正确的必要性。</br>
### 3、
在配置好了config.xml之后，就可以直接在命令行模式下运行java -jar sign-0.0.1-SNAPSHOT-build.jar命令了（原谅我还写不了前端），系统会自动判断组id是否已经注册过，和图片集路径配置是否有误，如果都正确，系统会自动进行图片初始化，如果是第一次注册，那么系统同事会进行照片的注册。一切准备完毕了之后，您会看到提示信息：“请输入人脸图片地址” ，这时您可以使用签到人的图片来进行签到了，具体是输入签到人图片的全路径，可以逐个进行签到。您也可以直接将签到人的图片放到一个文件夹中，然后将这个文件夹的全路径输入到命令行中，这时系统会自动对文件夹中的图片进行逐个识别签到。当识别分数大于80分，则会返回签到成功。如果签到完毕，您可以输入done命令，这时系统会保存结果，并自动终止程序，结果会保存在resource目录下的name.txt文本文件里，可以供您进行查看，而其他文件请不要进行改动，否则可能会发生无法预知的错误。

## 联系方式
E-maii:377028119@qq.com </br>
Author name:YiJun Duan
