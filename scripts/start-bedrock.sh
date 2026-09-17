#!/data/data/com.termux/files/usr/bin/bash
termux-wake-lock
cd ~/mc-bedrock && nohup php PocketMine-MP.phar > ~/bedrock.log 2>&1 &
echo "Serwer Bedrock uruchomiony"
