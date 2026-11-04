# uv pip install numpy 

import numpy as np 

arr2d = np.array([
  [1, 2],
  [3, 4]
])

print(arr2d.shape, arr2d.T, arr2d.reshape(-1, 1), arr2d[0:, 0:], arr2d[(arr2d > 2) & (arr2d < 4)]) # reshape(-1, 1) 第一个-1表示自动计算行数, 第二个1表示每行个数(<= 原始每行个数) 

print(f"按列求和: {arr2d.sum(axis = 0)} / 按行求和: {arr2d.sum(axis = 1)}")

# 广播运算 -- 每个元素操作一遍 
arr2d *= arr2d 

# 矩阵乘法 
newArr = arr2d @ arr2d

newList = arr2d.tolist() 


