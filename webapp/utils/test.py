import json
from pprint import pprint

import redis
import yaml
import os

# yaml_file = "db.yaml"
#
# fs = open(os.path.join("../", "conf.yaml"),encoding="UTF-8")
# datas = yaml.safe_load(fs)
# host = datas['hadoop']['host']
# port = datas['hadoop']['port']
# cluster = str(host)+":"+str(port)
# print(cluster)

if __name__ == '__main__':
    pool = redis.ConnectionPool(host="127.0.0.1", port=6379, max_connections=1024)
    r = redis.Redis(connection_pool=pool, decode_responses=True)
    user_top = json.loads(r.get("user_top"))
    pprint(user_top)