# python -m venv .venv 
# pip install uv 
# uv pip install pip-conf-mirror --> pip-conf-mirror --list --> pip-conf-mirror --uv <mirror_name>
# uv pip list 
# uv pip install/uninstall package-name 
# uv pip install -r requirements.txt 
# uv pip freeze > requirements.txt 
# print(dir(obj))

# --> pycharm 

input() 
print()

len()
max()
min()
sum()
round()

start, end, step = 0, 10, 1 
for i in range(start, end, step):
    pass 

var = 0
match var:
    case 0:
        pass
    case _:
        pass 

# del not and or in 
# int float bool 
# str list tuple set dict 
# global nonlocal return yield lambda 

for key, value in dict.items():
    pass 


iterable = iter(range(10)) # next(iterable)
for index, value in enumerate(iterable, start=0):
    pass


num = 0
def generator():
    yield num 


from functools import wraps
def mime(*args, **kwargs):
    def decorator(func):
        @wraps(func)
        def wrapper(*args, **kwargs):
            # before 
            result = func(*args, **kwargs)
            # after
            return result
        return wrapper
    return decorator


map_data = map(lambda x: x * 2, iterable)
filter_data = filter(lambda x: x % 2 == 0, iterable)


from abc import ABC, abstractmethod 

class AbstractClass(ABC): 

    def __init__(self, arg):
        self.arg = arg 

    @abstractmethod
    def abstract_method(self):
        pass

    @staticmethod 
    def static_method():
        pass 


class Base(AbstractClass):

    def __init__(self, arg):
        super().__init__(arg)


from enum import Enum 

class ErrorCode(Enum):

    SUCCESS = (0, "ok", "")
    PARAMS_ERROR = (40000, "请求参数错误", "")
    NULL_ERROR = (40001, "请求参数为空", "")
    NOT_LOGIN = (40100, "未登录", "")
    NOT_AUTH = (40101, "无权限", "")
    SYSTEM_ERROR = (50000, "系统内部异常", "") 

    def __init__(self, code, message, description):
        self.code = code 
        self.message = message 
        self.description = description 


with open("file", "rwab+", encoding = "utf-8") as file:
    file.read() 
    file.write()
    file.tell()
    file.seek(0)
    file.readline()
    file.writelines(["line1", "line2"])





