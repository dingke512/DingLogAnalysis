import pandas as pd
import jieba.analyse
import os
from pymysql import Connect


def file_name(file_dir):
    for root, dirs, files in os.walk(file_dir):
        print(len(files))
    return files


def mysql_execute(text):
    connect = Connect(host="127.0.0.1",port=3306,user="root", password="123456", database="dingke", charset="utf8")
    cursor = connect.cursor()
    cursor.execute(text)
    connect.commit()
    connect.close()


root = "E:/clear_data/weblog/"
file_list = file_name(root)
for item in file_list:
    day = item.replace(".csv","")
    print(day)
    print(root+item)
    df = pd.read_csv(root+item,error_bad_lines=False)
    df_list = df["word"].tolist()
    try :
        data = ','.join(df_list)
    except:
        data = ','.join('%s' %a for a in df_list)
    keywords = jieba.analyse.extract_tags(data, topK=30, withWeight=True, allowPOS=('n','nr','ns','vn', 'v'))
    for v in keywords:
        print(v[0],v[1])
        # mysql_execute("insert into ana_textrank  (date,word,weight) values ('%s','%s',%f)"%(day,str(v[0]),float(v[1])))
    del df
    del df_list

