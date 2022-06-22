#!/usr/bin/python# -*- coding: UTF-8 -*-
import time

import pandas as pd
import random
import argparse


df = pd.read_csv(r"./SogouQ样例精简版.txt",names=['time','userid','word','rank','url'], header=None,encoding="gbk",sep="\t")
df2 = df.drop(['time'],axis=1)
df2['word'] = df['word'].map(lambda x:x.replace("[","").replace("]",""))
data = df2
# data = df2.loc[df['word'] != ""]
parser = argparse.ArgumentParser(description='日志数量')
parser.add_argument('--n', type=int,help='日志数量',default=10)
args = parser.parse_args()
n = args.n


def format_time():
    time_str = time.strftime("%Y%m%d-%H:%M:%S",time.localtime())
    return time_str


def random_index():
    index = random.randint(0,data.shape[0])
    return index


def generate_log(count):
    f = open(file="web_query.log", mode="a", encoding="utf-8")
    while count>0:
        time_str = format_time()
        index = random_index()
        fake_data = "{localtime}\t{userid}\t{word}\t{rank}\t{click}\t{url}\t".format(
                    localtime=time_str,userid = data.iloc[index, 0],word=data.iloc[index, 1],
                    rank=data.iloc[index, 2].split(" ")[0],click=data.iloc[index, 2].split(" ")[1],
                    url=data.iloc[index, 3])
        print(fake_data)
        f.write(fake_data + "\n")
        count -= 1
        time.sleep(0.1)


if __name__ == '__main__':
    generate_log(n)
