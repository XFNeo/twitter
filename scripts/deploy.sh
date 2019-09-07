#!/usr/bin/env bash
mvn clean package

echo "Copying files..."
scp -i ~/.ssh/id_rsa \
  target/twitter-1.0-SNAPSHOT.jar \
  neo@10.245.30.1:/home/neo/

echo "Restarting server..."
ssh -i ~/.ssh/id_rsa neo@10.245.30.1 << EOF
pgrep java | xargs kill -9
nohup java -jar twitter-1.0-SNAPSHOT.jar > log.txt &
EOF
echo "Done"