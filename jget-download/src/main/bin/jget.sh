#!/bin/bash

CLASS=name.iaceob.jget.download.core.Jget
JAVA=java
JAVA_OPTS="-Xms512m -Xmx1536m  -server -XX:PermSize=64M -XX:MaxPermSize=256m"



PRG="$0"
while [ -h "$PRG" ]; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`/"$link"
    fi
done

PRGDIR=`dirname "$PRG"`
[ -z "$PRG_HOME" ] && PRG_HOME=`cd "$PRGDIR/.." >/dev/null; pwd`

CLASSPATH=
CLASSPATH=$(echo "$PRG_HOME"/lib/*.jar | tr ' ' ':')
CLASSPATH="${CLASSPATH}":"${PRG_HOME}/conf"

if [ -z $WORKERPIDFILE ]
then WORKERPIDFILE=/tmp/jget.pid
fi

WORKER_DAEMON_OUT="$WORKER_LOG_DIR/worker.out"


case $1 in
    start)
        echo -n "Starting worker ... "
        if [ -f $WORKERPIDFILE ]; then
            if kill -0 `cat $WORKERPIDFILE` > /dev/null 2>&1; then
                echo $command already running as process `cat $WORKERPIDFILE`.
                exit 0
            fi
        fi
        nohup $JAVA $JAVA_OPTS -cp $CLASSPATH $CLASS > /dev/null 2>&1 &

        if [ $? -eq 0 ]; then
            echo $!
            if  echo -n $! > "$WORKERPIDFILE"
            then
                sleep 1
                echo STARTED
            else
                echo FAILED TO WRITE PID
                exit 1
            fi
        else
            echo SERVER DID NOT START
            exit 1
        fi
        ;;

    stop)
        echo -n "Stopping worker ... "
        if [ ! -f "$WORKERPIDFILE" ]
        then
            echo "no worker to stop (could not find file $WORKERPIDFILE)"
        else
            echo $WORKERPIDFILE
            kill -9 $(cat "$WORKERPIDFILE")
            rm "$WORKERPIDFILE"
            echo STOPPED
        fi
        ;;

    restart)
        shift
        ./"$0" stop ${@}
        sleep 3
        ./"$0" start ${@}
        ;;
    status)
        if [ ! -f "$WORKERPIDFILE" ]
        then
            echo "no worker is running."
        else
            echo "worker is running (pid=$WORKERPIDFILE)"
        fi
        ;;
    *)
        echo "Usage: $0 {start|stop|restart|status}" >&2
esac