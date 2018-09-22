def firstDuplicate(a):
    array = set()
    arr = []
    min = -1
    for i in range(len(a)):
        if a[i] in array:
            min = i;
            break
        else:
            array.add(a[i])
    if (min != -1):
        return a[min]
    else:
        return -1
             
        
        
        