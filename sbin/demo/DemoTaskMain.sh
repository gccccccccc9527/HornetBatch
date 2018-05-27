#*******************************************
# 批量脚本：DemoTaskMain.sh
# 功能描述：demo 批量总入口
# 更新人：sunguanchun
# 更新时间：2018-05-09
#*******************************************

#!/bin/bash

#JAVA_HOME（注意生产目录）
J#AVA_HOME=/usr/java/jdk1.7.0_80
#项目部署目录，runModulesMain.sh中已经将project_root_dir加入环境变量
PROJECT_HOME=$project_root_dir
#项目根目录
APP_HOME=$PROJECT_HOME/HornetBatch/
#项目第三方jar目录
APP_LIB=$PROJECT_HOME/HornetBatch/lib
#项目class可执行编译文件目录
APP_BIN=$PROJECT_HOME/HornetBatch/bin

#加载环境变量
export LANG=utf-8
#export PROJECT_HOME
export APP_HOME
export APP_LIB
export APP_BIN

#加载项目第三方jar包
for i in $APP_LIB/*.jar
do 
	APP_CLASSPATH=$APP_CLASSPATH:$i
done

#调JVM执行main方法
cd $APP_BIN
$JAVA_HOME/bin/java -Ddefault.client.encoding=GBK -Dfile.encoding=GBK -Duser.language=Zh -Xms512m -Xmx2048m -cp ${APP_HOME}bin:./../lib:.:${JAVA_HOME}/jre/lib/rt.jar:${JAVA_HOME}/lib/tools.jar:${JAVA_HOME}/lib/dt.jar:${APP_CLASSPATH} org.hornet.modules.demo.DemoTaskMain

exit 0
