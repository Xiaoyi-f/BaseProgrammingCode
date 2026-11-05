"""LRU缓存"""
# 淘汰很久没用过的数据, 控制缓存容量, 优先保留热点数据 

# 基础示例 
from collections import OrderedDict 

class LRUCache:
    
    def __init__(self, capacity: int):
        self.cap = capacity 
        self.cache = OrderedDict() 

    def get(self, key):
        if key not in self.cache:
            return None 

        self.cache.move_to_end(key)

        return self.cache[key]

    def put(self, key, value):
        if key in self.cache:
            self.cache.move_to_end(key)
            
        self.cache[key] = value

        if len(self.cache) > self.cap:
            self.cache.popitem(last=False)


# 测试
if __name__ == "__main__":
    lru = LRUCache(3)
    lru.put("A", 1)
    lru.put("B", 2)
    lru.put("C", 3)
    print(lru.get("A"))
    lru.put("D", 4)
    print(lru.cache)
