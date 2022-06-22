# !/usr/bin/env python
# -*- coding: utf-8 -*-
from django.shortcuts import HttpResponseRedirect,HttpResponse


try:
    from django.utils.deprecation import MiddlewareMixin
except ImportError:
    MiddlewareMixin = object  # Django 1.4.x - Django 1.9.x


# 继承MiddlewareMixin类

class SimpleMiddleware(MiddlewareMixin):

    def process_request(self, request):
        # print(request.path)
        if request.path != '/' and request.path != '/login_ajax_check/':
            if request.session.get('userid', None):
                pass
            else:
                response = HttpResponse()
                response.write('<h2>HTTP 200 :请求成功</h2>')
                response.write('<hr/>')
                response.write("<p style='color:#ff0000' >账号校验失败，请登录后再进入</p>")
                return response

