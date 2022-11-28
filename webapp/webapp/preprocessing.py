import json
import os
import time
from webapp import settings
from webapp.db_conn import *
from webapp.db_conn import dbname

# 整体趋势数据处理
def data_trend(start='20060801', end='20060831'):
    ana_user = mysql_query_all("SELECT * from ana_user where date >= '%s' and date <= '%s'  " % (start, end))
    if len(ana_user) == 0:
        return 0
    total = [i[3] for i in ana_user]
    uv = [i[1] for i in ana_user]
    date = [i[4] for i in ana_user]
    ana_url = mysql_query_all("SELECT * from ana_url  where date >= '%s' and date <= '%s' " % (start, end))
    url_count = [i[1] for i in ana_url]
    ana_keyword = mysql_query_all("SELECT * from ana_keyword   where date >= '%s' and date <= '%s'  " % (start, end))
    word_count = [i[1] for i in ana_keyword]
    sum_total = mysql_query_one("SELECT sum(total) from ana_user where date >= '%s' and date <= '%s'" % (start, end))
    trend = {"date": date, "total": total, "uv": uv, "url_count": url_count, "word_count": word_count}
    word_max = mysql_query_all("SELECT date,keyword,max(count) FROM ana_keyword_topn WHERE date >= '%s' AND date <='%s' GROUP BY date"%(start,end))
    word_arr = []
    avg_length = mysql_query_all("SELECT avg_length,date from ana_keyword  where date >= '%s' and date <= '%s' " % (start, end))
    data_x = [x[1] for x in avg_length]
    data_y = [y[0] for y in avg_length]
    for i in word_max:
        word_arr.append({"date":i[0],"word":i[1],"count":i[2]})
    result = {"trend": json.dumps(trend), "sum_total": int(sum_total[0])/10000, "word_data":word_arr,"data_x":data_x,"data_y":data_y}

    return result


# 用户分析数据
def data_userid(date='20060801', n=20):
    ana_userid = mysql_query_one("SELECT * from ana_user where date = '%s'  " % (date))
    user_topn = mysql_query_all("SELECT * FROM ana_user_topn where date ='%s' " % (date))
    if ana_userid is None:
        return 0
    arr_topn = []
    for i in range(n):
        item = {"rank": i + 1, "userid": user_topn[i][1], "count": user_topn[i][2]}
        arr_topn.append(item)
    result = {"date":date,"user_count": ana_userid[1], "avg_user": ana_userid[2], "total": ana_userid[3], "topn": arr_topn}
    return result


# 点击页面分析数据
def data_url(date='20060801',n=20):
    ana_url = mysql_query_one("SELECT * from ana_url where date = '%s'  " % (date))
    url_topn = mysql_query_all("SELECT * FROM ana_url_topn where date ='%s' " % (date))
    ana_userid = mysql_query_one("SELECT * from ana_user where date = '%s'  " % (date))
    if ana_url is None:
        return 0
    arr_topn = []
    for i in range(n):
        item = {"rank": i + 1, "url": url_topn[i][1], "count": url_topn[i][2]}
        arr_topn.append(item)
    result = {"date":date,"url_count": ana_url[1], "avg_url": ana_url[2], "topn": arr_topn, "total": ana_userid[3]}
    return result


# 搜索内容分析数据
def data_keyword(date='20060801',n=20):
    ana_word = mysql_query_one("SELECT * from ana_keyword where date = '%s'  " % (date))
    word_topn = mysql_query_all("SELECT * FROM ana_keyword_topn where date ='%s' " % (date))
    ana_userid = mysql_query_one("SELECT * from ana_user where date = '%s'  " % (date))
    ana_textrank = mysql_query_all("SELECT word,weight from ana_textrank where date = '%s'  " % (date))
    if ana_word is None:
        return 0
    arr_topn = []
    for i in range(n):
        item = {"rank": i + 1, "word": word_topn[i][1], "count": word_topn[i][2]}
        arr_topn.append(item)
    key_arr = []
    for j in ana_textrank:
        key_arr.append({"name":j[0],"value":j[1]})

    result = {"date":date,"word_count": ana_word[1], "word_avg": ana_word[2], "avg_length": ana_word[3],"topn": arr_topn, "total": ana_userid[3],"key_arr":key_arr}
    return result


# 动态数据
def data_stream():
    pool = redis.ConnectionPool(host="127.0.0.1", port=6379, max_connections=1024)
    r = redis.Redis(connection_pool=pool, decode_responses=True)
    try:
        user_count = r.get("user_count")
        page_count = r.get("page_count")
        word_count = r.get("word_count")
        flow_count = r.get("flow_count")
        user_total = r.get("user_total")
        url_total = r.get("page_total")
        pool = redis.ConnectionPool(host="127.0.0.1", port=6379, max_connections=1024)
        r = redis.Redis(connection_pool=pool, decode_responses=True)
        user_top = json.loads(r.get("user_top"))
        arr_user = []
        rank = 1
        for key in user_top.keys():
            arr_user.append({"rank":rank,"key":key,"count":user_top[key]})
            rank += 1
        page_top = json.loads(r.get("page_top"))

        arr_page = []
        rank = 1
        for key in page_top.keys():
            arr_page.append({"rank":rank,"key":key,"count":page_top[key]})
            rank += 1
        result = {"user_count":int(user_count),"page_count":int(page_count),"word_count":int(word_count), "flow_count":int(flow_count),
                  "user_total":int(user_total),"page_total":int(url_total),"arr_user":arr_user,"arr_page":arr_page}
    except:
        result = {"user_count":0,"page_count":0,"word_count":0,"flow_count":0,
                  "user_total":0,"page_total":0,"arr_user":[],"arr_page":[]}
    return result


# 用户权限
def user_pri(userid):
    res = mysql_query_one("select priority from db_user where userid = '%s' "%(userid))
    return res


# 查询全部用户信息
def user_query_all():
    user_list = mysql_query_all("SELECT userid,username,priority,gender,age,phone,image,password FROM db_user a LEFT JOIN db_user_info b USING(userid)")
    res = []
    rank = 1
    for usr in user_list:
        res.append({"rank":rank ,"userid":usr[0],"username":usr[1],"priority":usr[2],"gender":usr[3],"age":usr[4],"phone":usr[5],"image":'/media/'+usr[6],"password":usr[7]})
        rank += 1
    return res


# 根据ID查询
def query_by_id(userid):
    userinfo = mysql_query_one("select userid,username,gender,age,phone,image  from db_user a left JOIN db_user_info b  USING(userid) WHERE userid = '%s' "%(userid))
    res = {"userid":userinfo[0],"username":userinfo[1],"gender":userinfo[2],"age":userinfo[3],"phone":userinfo[4],"image":'/media/'+userinfo[5]}
    return res


# 添加一个用户
def insert_user(userid:str,pasword:str,priority:int):
    sql = "insert into db_user (userid,password,priority) values('%s','%s',%d)"%(userid,pasword,priority)
    use_one(sql)


# 添加一个用户基本信息
def insert_userinfo(userid:str,username:str,gender:int,age:int,phone:str,image:str):
    sql = "insert into db_user_info (userid,username,gender,age,phone,image) values('%s','%s',%d,%d,'%s','%s')"%(userid,username,gender,age,phone,image)
    use_one(sql)


# 根据id删除所有信息
def del_all(userid):
    connect = Connect(host="127.0.0.1",port=3306,user="root", password="123456", database=dbname, charset="utf8")
    cursor = connect.cursor()
    try:
        image = mysql_query_one("select image from db_user_info where userid='%s'"%(userid))[0]
        cursor.execute("DELETE FROM db_user WHERE userid = '%s'"%(userid))
        cursor.execute( "DELETE FROM db_user_info WHERE userid = '%s'"%(userid))
        connect.commit()
        cursor.close()
        connect.close()
        if image != 'default.jpg':
            os.remove('%s\\%s'%(settings.MEDIA_ROOT,image[0]))
    except Exception as e:
        print(e)
        connect.rollback()
        connect.close()
        print("error")


# 查询用户手机号
def get_phone(phone):
    state = mysql_query_one("SELECT * FROM db_user_info WHERE phone = '%s'"%(phone))
    if state:
        return False
    return True


#  更新用户的信息
def update_userinfo(userid,username,age,gender,phone):
    sql = " UPDATE db_user_info SET username ='%s',age=%d,gender=%d,phone='%s' WHERE userid ='%s'; "%(username,age,gender,phone,userid)
    use_one(sql)


def update_password():
    pass


# UID生成
class RuidGet(object):

    @classmethod
    def get_str_ruid(cls):
        # 获取16进制字符串唯一id
        base_time = round(time.mktime(time.strptime('1970-01-02 00:00:00', '%Y-%m-%d %H:%M:%S'))*10**3)
        ruid = round(time.time()*10**3) - base_time
        time.sleep(0.001)
        return str(hex(ruid)).replace('0x', '')

    @classmethod
    def get_int_ruid(cls):
        # 获取10进制整数唯一id
        base_time = round(time.mktime(time.strptime('1970-01-02 00:00:00', '%Y-%m-%d %H:%M:%S')) * 10 ** 3)
        ruid = round(time.time() * 10 ** 3) - base_time
        time.sleep(0.001)
        return str(ruid)