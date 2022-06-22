from django.http import JsonResponse, HttpResponse
from django.shortcuts import render,redirect,reverse
import yaml
import platform,psutil
from webapp.preprocessing import *


def index(request):
    return render(request=request,template_name='login.html',context=None)


"""用户模块user"""


# ajax异步校验
def check_login(request):
    if request.method == 'POST':
        userid = request.POST.get('userid')
        password = request.POST.get('password')
        query = "select * from db_user where userid='%s' and password ='%s'"%(userid,password)
        data = mysql_query_one(query)
        if not data:
            return JsonResponse({"state":0})
        else:
            request.session["userid"] = userid
            request.session.set_expiry(0)
            return JsonResponse({"state":1})


# 登录进入主页
def login(request):
    # 经过过滤第二次校验，判断session_key是否存在
    key = request.session.session_key
    userid = request.session.get("userid")
    if not key:
        response = HttpResponse()
        response.write('<h1>未登录禁止访问！</h1>')
        return response
    user = query_by_id(userid)
    conf = os.getcwd()+"\\conf.yaml"
    fs = open(conf,encoding="UTF-8")
    datas = yaml.safe_load(fs)
    host = datas['hadoop']['host']
    port = datas['hadoop']['port']
    head = datas['head']['visual']
    text = ''
    if head:
        text = datas['head']['text']
    hadoop = "http://"+str(host)+":"+str(port)

    return render(request=request,template_name='home.html',context={"user":user,"hadoop":hadoop,"text":text})


# 主页介绍界面
def welcome(request):
    return render(request=request,template_name="welcome.html")


# 退出登录
def logout(request):
    request.session.flush()
    return redirect("/")


# 用户信息查询
def user_query(request):
    if request.method == 'GET':
        userid = request.session.get("userid")
        p = user_pri(userid)
        if p is None or p[0]==0:
            response = HttpResponse()
            response.write('<h2>HTTP 200 :请求成功</h2>')
            response.write('<hr/>')
            response.write("<p style='color:#ff0000' >权限校验失败，您没有权限，如需使用，请联系管理员</p>")
            return response
        elif p[0] == 1:
            user_list = user_query_all()
            return render(request=request,template_name="usermanager.html",context={"user_list":user_list})


# 用户查询
def user_condition(request):
    if request.method == 'GET':
        userid = request.GET.get("userid")
        phone = request.GET.get("phone")
        priority = request.GET.get("priority")
        # print(userid,phone,priority)
        # 全为空
        if userid == "" and phone  == "" and priority  == "":
            return redirect("/user/manager")
        # id不为空
        elif userid != "" and phone  == "" and priority  == "":
            user_list = mysql_query_all("SELECT userid,username,priority,gender,age,phone,image,password FROM db_user a LEFT JOIN db_user_info b USING(userid) where userid ='%s'" %(userid))
            res = []
            rank = 1
            for usr in user_list:
                res.append({"rank":rank ,"userid":usr[0],"username":usr[1],"priority":usr[2],"gender":usr[3],"age":usr[4],"phone":usr[5],"image":'/media/'+usr[6],"password":usr[7]})
                rank += 1
            return render(request=request,template_name="usermanager.html",context={"user_list":res})
        # 电话不为空
        elif userid  == "" and phone != "" and priority  == "":
            user_list = mysql_query_all("SELECT userid,username,priority,gender,age,phone,image,password FROM db_user "
                                        "a LEFT JOIN db_user_info b USING(userid) where phone ='%s'" %(phone))
            res = []
            rank = 1
            for usr in user_list:
                res.append({"rank":rank ,"userid":usr[0],"username":usr[1],"priority":usr[2],"gender":usr[3],"age":usr[4],"phone":usr[5],"image":'/media/'+usr[6],"password":usr[7]})
                rank += 1
            return render(request=request,template_name="usermanager.html",context={"user_list":res})
        # 权限不为空
        elif userid =="" and phone =="" and priority !="":
            user_list = mysql_query_all("SELECT userid,username,priority,gender,age,phone,image,password FROM db_user "
                                            "a LEFT JOIN db_user_info b USING(userid)  where priority = %d" %(int(priority)))
            res = []
            rank = 1
            for usr in user_list:
                res.append({"rank":rank ,"userid":usr[0],"username":usr[1],"priority":usr[2],"gender":usr[3],"age":usr[4],"phone":usr[5],"image":'/media/'+usr[6],"password":usr[7]})
                rank += 1
            return render(request=request,template_name="usermanager.html",context={"user_list":res})

        # id 和 电话
        elif userid != "" and phone  != "" and priority  == "":
            user_list = mysql_query_all("SELECT userid,username,priority,gender,age,phone,image,password FROM db_user "
                                        "a LEFT JOIN db_user_info b USING(userid)  where userid ='%s' and phone='%s'" %(userid,phone))
            res = []
            rank = 1
            for usr in user_list:
                res.append({"rank":rank ,"userid":usr[0],"username":usr[1],"priority":usr[2],"gender":usr[3],"age":usr[4],"phone":usr[5],"image":'/media/'+usr[6],"password":usr[7]})
                rank += 1
            return render(request=request,template_name="usermanager.html",context={"user_list":res})
        # id 和 权限
        elif userid != "" and phone  == "" and priority  != "":
            user_list = mysql_query_all("SELECT userid,username,priority,gender,age,phone,image,password FROM db_user "
                                        "a LEFT JOIN db_user_info b USING(userid)  where userid ='%s' and priority=%d" %(userid,int(priority)))
            res = []
            rank = 1
            for usr in user_list:
                res.append({"rank":rank ,"userid":usr[0],"username":usr[1],"priority":usr[2],"gender":usr[3],"age":usr[4],"phone":usr[5],"image":'/media/'+usr[6],"password":usr[7]})
                rank += 1
            return render(request=request,template_name="usermanager.html",context={"user_list":res})
        # 电话 和 权限
        elif userid == "" and phone != "" and priority != "":
            user_list = mysql_query_all("SELECT userid,username,priority,gender,age,phone,image,password FROM db_user "
                                            "a LEFT JOIN db_user_info b USING(userid)  where phone ='%s' and priority=%d" %(phone,int(priority)))
            res = []
            rank = 1
            for usr in user_list:
                res.append({"rank":rank ,"userid":usr[0],"username":usr[1],"priority":usr[2],"gender":usr[3],"age":usr[4],"phone":usr[5],"image":'/media/'+usr[6],"password":usr[7]})
                rank += 1
            return render(request=request,template_name="usermanager.html",context={"user_list":res})
        # 都不为空
        elif userid != "" and phone != "" and priority != "":
            user_list = mysql_query_all("SELECT userid,username,priority,gender,age,phone,image,password FROM db_user "
                                        "a LEFT JOIN db_user_info b USING(userid)  where userid='%s' and phone ='%s' and priority=%d" %(userid,phone,int(priority)))
            res = []
            rank = 1
            for usr in user_list:
                res.append({"rank":rank ,"userid":usr[0],"username":usr[1],"priority":usr[2],"gender":usr[3],"age":usr[4],"phone":usr[5],"image":'/media/'+usr[6],"password":usr[7]})
                rank += 1
            return render(request=request,template_name="usermanager.html",context={"user_list":res})

# 添加新用户
def user_add(request):
    if request.method == 'POST':
        username = request.POST.get("username")
        age = request.POST.get("age")
        gender = request.POST.get("gender")
        phone = request.POST.get("phone")
        password = request.POST.get("password")
        priority = request.POST.get("priority")
        image = 'default.jpg'
        userid = RuidGet.get_int_ruid()
        state = get_phone(phone)
        connect = Connect(host="127.0.0.1",port=3306,user="root", password="123456", database="dingke", charset="utf8")
        cursor = connect.cursor()
        if state:
            try:
                cursor.execute("insert into db_user (userid,password,priority) values('%s','%s',%d)"%(str(userid),password,int(priority)))
                cursor.execute("insert into db_user_info (userid,username,age,gender,phone,image) values ('%s','%s',%d,%d,'%s','%s')"
                               %(userid,username,int(age),int(gender),phone,image))
                cursor.close()
                connect.commit()
                state = 1
                msg = "已成功注册请登录使用"
            except:
                connect.rollback()
                connect.close()
                userid = "None"
                state = 0
        else:
            userid = "None"
            state = 0
            msg = "此手机号已经绑定"

        return render(request=request,template_name="register.html",context={"userid":userid,"state":state,"msg":msg})


# 删除用户
def user_delete(request):
    if request.method == 'POST':
        userid = request.POST.get("userid")
        del_all(userid)
    return redirect("/user/manager")


# 修改用户
def admin_update(request):
    if request.method == 'POST':
        userid = request.POST.get("userid")
        password = request.POST.get("password")
        username = request.POST.get("username")
        age = request.POST.get("age")
        gender = request.POST.get("gender")
        phone = request.POST.get("phone")
        priority = request.POST.get("priority")
        connect = Connect(host="127.0.0.1",port=3306,user="root", password="123456", database="dingke", charset="utf8")
        cursor = connect.cursor()
        try:
            cursor.execute("UPDATE db_user SET password='%s', priority=%d WHERE userid ='%s';"%(password,int(priority),userid))
            cursor.execute("UPDATE db_user_info SET username='%s', age =%d,gender=%d,phone='%s' WHERE userid ='%s';"%(username,int(age),int(gender),phone,userid))
            connect.commit()
            cursor.close()
            connect.close()
        except Exception as e:
            print(e)
            connect.rollback()
            connect.close()
            print("error")
    return redirect("/user/manager")


# 用户自己修改
def user_update(request):
    pic = request.FILES.get('pic')
    userid = request.POST.get("userid")
    username = request.POST.get("username")
    age = request.POST.get("age")
    gender = request.POST.get("gender")
    phone = request.POST.get("phone")
    response = HttpResponse()
    if pic is not None:
        uid = RuidGet.get_str_ruid()
        pic_name = uid+'.jpg'
        save_path = '%s\\%s'%(settings.MEDIA_ROOT,pic_name)
        with open(save_path, 'wb') as f:
            # 获取上传文件的内容并写到创建文件中; pic.chunks():分块的返回文件
            for content in pic.chunks():
                f.write(content)
        try:
            image = mysql_query_one("select image from db_user_info where userid='%s'"%(userid))
            os.remove('%s\\%s'%(settings.MEDIA_ROOT,image[0]))
            use_one("UPDATE db_user_info SET image='%s' WHERE userid ='%s';"%(pic_name,userid))
        except:
            response.write("<h2>头像修改失败</h2>")

    update_userinfo(userid,username,int(age),int(gender),phone)
    response.write("<h2 style = 'color:#ff0000'>修改成功，刷新后生效</h2>")
    return response


# 修改密码界面
def user_pwd(request):
    return render(request=request,template_name="updatepwd.html")


# 信息校验并修改
def ajax_pwd(request):
    if request.method == 'POST':
        userid = request.session.get("userid")
        phone = request.POST.get("phone")
        pwd = request.POST.get("pwd")
        npwd = request.POST.get("npwd")
        print(npwd)
        my_phone = mysql_query_one("select phone from db_user_info where userid= '%s' " % userid)[0]
        my_pwd = mysql_query_one("select password from db_user where userid= '%s' " % userid)[0]
        err_info = ""
        if my_phone != phone:
            err_info = err_info+"手机号错误"
            result = {"message":err_info,"state":0}
        elif pwd != my_pwd:
            err_info = err_info+"旧密码错误"
            result = {"message":err_info,"state":0}
        elif my_pwd == npwd:
            err_info = err_info+"旧密码与新密码相同"
            result = {"message":err_info,"state":0}

        else:
            result = {"message":err_info,"state":1}

        return JsonResponse(result)


# 设置界面
def user_setting(request):
    if request.method == 'GET':
        userid = request.session.get("userid")
        me = query_by_id(userid)

    return render(request=request,template_name="setting.html",context={"user":me})


"""实时数据模块"""


# 实时界面
def streaming(request):
    return render(request=request,template_name="stream.html")


# 实时数据
def dynamic_data(request):
    data = data_stream()
    return JsonResponse(data,safe=False)


"""离线分析"""


# 整体趋势
def ana_trend(request):
    start = request.GET.get('start')
    end = request.GET.get('end')
    if start is None or end is None:
        result = data_trend()
    else:
        start = start.replace("-","")
        end = end.replace("-","")
        result = data_trend(start=start,end=end)

    return render(request=request,template_name="trend.html",context={"result":result})


# 用户分析
def ana_user(request):
    date = request.GET.get('date')
    n = request.GET.get('n')
    if date is None:
        result = data_userid()
    else:
        date = date.replace("-","")
        result = data_userid(date,int(n))
    return render(request=request,template_name="user_ana.html",context={"result":result})


# 页面分析
def ana_url(request):
    date = request.GET.get('date')
    n = request.GET.get('n')
    if date is None:
        result = data_url()
    else:
        date = date.replace("-","")
        result = data_url(date,int(n))
    return render(request=request,template_name="url_ana.html",context={"result":result})


# 查询词分析
def ana_keywords(request):
    date = request.GET.get('date')
    if date is None:
        result = data_keyword()
    else:
        date = date.replace("-","")
        result = data_keyword(date)

    return render(request=request,template_name="keywords.html",context={"result":result})


# LDA界面
def lda(request):
    return render(request=request,template_name="lda_visual.html")


# LDA自定义查询日期
def ana_lda(request):
    date = request.GET.get('date')
    if date is None :
        date = '20060801'
    else:
        date = date.replace("-","")
    tmp = "lda\\"+date+".html"
    path = os.path.abspath(os.path.dirname(os.path.dirname(__file__)))+"\\templates\\"
    file = path+tmp

    if os.path.exists(file):
        return render(request=request,template_name=tmp)
    else:
        response = HttpResponse()
        response.write("<h3>HTTP 200 <h3>")
        response.write("<h3>当前日期不存在<h3>")
        return response


# 搜索结果分析
def ana_result(request):
    pass


"""资源模块"""


# local page
def local_base(request):
    return render(request=request,template_name="",context={})


# local static information
def local_resources(request):
    total = str(round(psutil.virtual_memory().total / (1024.0 * 1024.0 * 1024.0), 2))+'GB'
    result = []
    result.append({"name":"系统","value":platform.system()})
    result.append({"name":"操作系统名称及版本号","value": platform.platform()})
    result.append({"name":"CPU生产商","value":platform.machine()})
    result.append({"name":"CPU信息","value":platform.processor()})
    result.append({"name":"操作系统的位数","value":platform.architecture()[0]})
    result.append({"name":"计算机的网络名称","value":platform.node()})
    result.append({"name":"物理cpu个数","value":psutil.cpu_count(logical=False)})
    result.append({"name":"总内存","value":total})

    return render(request=request,template_name="resource.html",context={"result":result})


# dynamic data
def data_resources(request):
    free = float(round(psutil.virtual_memory().free / (1024.0 * 1024.0 * 1024.0), 2))

    data = {"memory_use_rate":float(psutil.virtual_memory().percent),"cpu_use_rate":float(psutil.cpu_percent(interval=1)),
            "memory_free":free,}
    return JsonResponse(data)


# cluster
def cluster_resources():
    pass