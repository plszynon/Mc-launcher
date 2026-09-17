#!/data/data/com.termux/files/usr/bin/bash
termux-wake-lock
cd ~/mc-java && nohup java -Xms1G -Xmx2G -jar paper.jar nogui > ~/java.log 2>&1 &
echo "Serwer Java uruchomiony"
