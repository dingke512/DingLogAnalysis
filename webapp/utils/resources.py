
import urllib3
import json

def check_unhealth_nodes() :
    url = 'http://ip:port/ws/v1/cluster/nodes'
    req = urllib3.Request(url)
    res_data = urllib3.urlopen(req)
    nodes = json.loads(res_data.read())['nodes']['node']
    for node in nodes :
        if node['state'] == u'UNHEALTHY' :
            print("master ip:%s unhealthy nodes ip : %s" % (master_ip,node['nodeHostName']))

if __name__ == "__main__":
    check_unhealth_nodes()