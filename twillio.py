 


### Twillio challenge##
def missingWords(s, t):
    res = []
    
    s = s.split()
    t = t.split()
    
    i = j = 0
    
    while i < len(s) and j < len(t):
        while i < len(s) and s[i] != t[j]:
            res.append(s[i])
            i += 1
        i += 1
        j += 1
    res.extend(s[i:])
    
    return res





#### number of hosts that sent requests  ####   
filename = input()
hosts = {}
with open(filename, "r") as f:
    for line in f:
        host = line.split()[0]
        if host in hosts:
            hosts[host] += 1
        else:
            hosts[host] = 1

with open('records_' + filename, 'w') as output:
    for host, count in hosts.items():
        string = host + ' ' + str(count)
        output.write("%s\n" % string)

