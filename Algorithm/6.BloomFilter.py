"""布隆过滤器"""
# 判断 [这个值一定不存在 / 可能存在] 档不存在的数据, 减少缓存穿透 

# 基础示例 
import math
import hashlib 

class BloomFilter:
    
    def __init__(self, n = 10000, p = 0.01):
        # n 预估元素数量 / p 期望误判率 
        self.size = int(-n * math.log(p) / (math.log(2) ** 2))
        self.k = int(self.size / n * math.log(2))
        self.big_array = [0] * self.size 

    def _hash(self, source_data, seed):
        # md5 算法 数学不可逆 
        h = hashlib.md5((str(seed) + source_data).encode()).hexdigest()
        return int(h, 16) % self.size

    def add(self, item):
        for i in range(self.k):
            idx = self._hash(item, i)
            self.bit_array[idx] = 1

    def contains(self, item):
        for i in range(self.k):
            idx = self._hash(item, i)
            if self.bit_array[idx] == 0:
                return False
        return True


# 测试
if __name__ == "__main__":
    bf = BloomFilter(n=1000, p=0.01)
    bf.add("apple")
    bf.add("banana")
    print(bf.contains("apple"))
    print(bf.contains("orange"))
        


