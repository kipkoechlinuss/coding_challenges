def print_tree(tree):
	result = []
	if tree == None:
		return 
	print_tree(tree.left)
	result.append(tree.value)
	print_tree(tree.right)
	result.append(tree.value)
	return result
	
