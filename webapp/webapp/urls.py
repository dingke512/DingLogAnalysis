"""webapp URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path, re_path
from django.views.static import serve

from webapp import settings
from webapp.views import *
# 定制站点头部和标题
admin.site.site_title = 'DING'  # 站点标题
admin.site.site_header = 'DING'  # 站点头部

urlpatterns = [
    # path('admin/', admin.site.urls),
    # 静态图片访问路由
    re_path(r'media/(?P<path>.*)$',serve,{'document_root':settings.MEDIA_ROOT}),
    # 小标
    path('favicon.ico', serve,{'path': 'images/favicon.ico', 'document_root': os.path.join(settings.BASE_DIR, "static")}),

    path('',index),
    # 用户
    path('login_ajax_check/',check_login),
    path('login/',login),
    path('welcome/',welcome),
    path('logout/',logout),

    # 实时
    path('streaming/',streaming),
    path('streaming/data/',dynamic_data),

    # 离线
    path('offline/ana_trend',ana_trend),
    path('offline/ana_user',ana_user),
    path('offline/ana_url',ana_url),
    path('offline/ana_keywords',ana_keywords),
    path('offline/ana_result',ana_result),
    path('offline/lda',lda),
    path('offline/ana_lda',ana_lda),

    # 用户管理
    path('user/manager',user_query),
    path('user/add',user_add),
    path('user/query/condition',user_condition),
    path('user/delete',user_delete),
    path('user/update',user_update),
    path('user/admin/update',admin_update),
    path('user/setting',user_setting),
    path('user/updatepwd',user_pwd),
    path('user/ajax_pwd',ajax_pwd),
    # 资源监控
    path('resources/local',local_resources),
    path('resource/data',data_resources),
]
