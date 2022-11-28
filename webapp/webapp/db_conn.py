import os

import redis
import yaml
from pymysql import Connect

conf = os.getcwd() + "\\conf.yaml"
fs = open(conf, encoding="UTF-8")
datas = yaml.safe_load(fs)
dbname = datas['dbname']

def mysql_query_all(sql_text):
    connect = Connect(host="127.0.0.1",port=3306,user="root", password="123456", database=dbname, charset="utf8")
    cursor = connect.cursor()
    cursor.execute(sql_text)
    data = cursor.fetchall()
    cursor.close()
    connect.close()
    return data


def mysql_query_one(sql_text):
    connect = Connect(host="127.0.0.1",port=3306,user="root", password="123456", database=dbname, charset="utf8")
    cursor = connect.cursor()
    cursor.execute(sql_text)
    data = cursor.fetchone()
    cursor.close()
    connect.close()
    return data


def use_one(sql_text):
    connect = Connect(host="127.0.0.1",port=3306,user="root", password="123456", database=dbname, charset="utf8")
    cursor = connect.cursor()
    cursor.execute(sql_text)
    connect.commit()
    cursor.close()
    connect.close()


def redis_conn():
    pool = redis.ConnectionPool(host="127.0.0.1", port=6379,max_connections=1024)
    conn = redis.Redis(connection_pool=pool,decode_responses=True)
    return conn


if __name__ == '__main__':
    r = redis_conn()
    print(r.get("uv"))

