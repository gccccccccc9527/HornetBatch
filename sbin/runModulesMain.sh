#*************************************************************************************
# 跑批总入口：runModulesMain.sh
# 功能描述：通过获取传入参数，执行对应模块下的脚本，分别储存对应模块的日志，用作统一调度。
# 传入参数：arg1 -> module_name 模块名
#           arg2 -> module_shell_name 模块对应主程序shell脚本名
# 示例：nohup sh /{path}/runModulesMain.sh {module_name} {module_shell_name} > /dev/null 2>&1 & 
# 更新人：sunguanchun
# 更新时间：2018-05-09
# 注意：请注意crontab的时间配置
#*************************************************************************************

#!/bin/bash

#当前系统时间
cur_log_date=`date +%Y%m%d`
#当前模块名
cur_module_name="$1"
#当前跑批执行的脚本名
cur_sh_name="$2"

#1.检查输入参数 module_name 模块名
if [ $1 == "" ];then 
    echo "执行runModulesMain.sh需要传入2个参数："
    echo "arg1 -> module_name 模块名"
    echo "arg2 -> module_shell_name 模块对应主程序shell脚本名"
	exit 1; 
fi

#2.检查输入参数 module_shell_name 模块对应主程序shell脚本名
if [ $2 == "" ];then 
    echo "执行runModulesMain.sh需要传入2个参数："
    echo "arg1 -> module_name 模块名"
    echo "arg2 -> module_shell_name 模块对应主程序shell脚本名"
	exit 1; 
fi

#当前执行脚本文件名（即Xxx.sh中的Xxx）
cur_sh_file_name=${$cur_sh_name##*.}

#项目部署目录
project_root_dir=/home/weblogic/test/coupon
#项目lib目录
project_lib_dir=$project_root_dir/HornetBatch4Sh/lib
#项目bin目录
project_bin_dir=$project_root_dir/HornetBatch4Sh/bin
#项目sbin脚本目录
project_sbin_dir=$project_root_dir/HornetBatch4Sh/sbin
#项目logs目录
project_logs_dir=$project_root_dir/HornetBatch4Sh/logs
#当前日志文件名
cur_log_name=$cur_sh_file_name.$cur_log_date.log
#当前日志文件存放目录
cur_log_dir=$project_logs_dir/$cur_module_name

export project_root_dir

#3.检查日志目录
cd $project_logs_dir
if [ ! -d $cur_module_name ];then
	mkdir $cur_module_name
fi

#3.执行module对应的脚本，并写入日志
sh $project_sbin_dir/$cur_module_name/"$2".sh >> $cur_log_dir/$cur_log_name





















