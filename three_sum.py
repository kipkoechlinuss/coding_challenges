import unittest
class Test(unittest.TestCase):
	def test(self):
		lst = [-1, 0, 1, 2, -1, -4]
		print(three_some(lst,0))
		self.assertTrue()





def three_some(arr,target):
	result = []
	arr.sort()
	n = len(arr)-2
	for i in range(n):
		a = arr[i]
		start = i+1
		end = n-1
		while (start < end):
			b = arr[start]
			c = arr[end]
			if (a+b+c) == target:
				result.append(a) 
				result.append(b)
				result.append(c)
			elif (a+b+c > 0):
				end = end - 1
			else:
				start = start + 1
	return result
if __name__ == '__main__':
	unittest.main()