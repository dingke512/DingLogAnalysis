import time

from selenium import webdriver
from selenium.webdriver.common.by import By

options = webdriver.ChromeOptions()

options.add_argument('lang=zh_CN.UTF-8')
# 规避
options.add_experimental_option("excludeSwitches", ["enable-automation"])
options.add_experimental_option('useAutomationExtension', False)

driver = webdriver.Chrome(options=options, executable_path='D:\chromedriver.exe')
# 最大等待时长   每隔半秒查看返回结果

driver.get("http://www.baidu.com")


input_text = driver.find_element(By.ID,"kw")
input_text.send_keys("hello world")
# userid = driver.find_element(By.ID,"userid")
# password = driver.find_element(By.ID,"password")
# submit = driver.find_element(By.ID,"btnLogin")
# # # 输入
# userid.send_keys(1646616219006)
# password.send_keys(123456)
# submit.click()
