# pip install pandas
# 处理Excel: pip install openpyxl
# 处理JSON/CSV: 无需额外安装依赖 

import pandas as pd

# 导入文件 
# 1. 读取 CSV 文件
df = pd.read_csv("数据.csv", encoding="utf-8")
# 可选参数:encoding="gbk" 解决中文乱码 -- 注意编码规范 

# 2. 读取 JSON 文件 格式: [{}, {}, ...]
df = pd.read_json("数据.json", orient="records")

# 3. 读取 Excel 文件 (xlsx)
df = pd.read_excel("数据.xlsx")

# 查看数据概况 
df.head()  # 返回头部n行, df.head(10)前10行
df.tail()  # 返回尾部n行, df.tail(10)后10行
df.shape   # 返回 (行数, 列数)
df.columns # 返回全部列名
df.dtypes  # 每一列的数据类型: object=文本
df['key'].dtype # 单独看某一列类型
df.describe() # 数字列的统计: 均值、最大最小等
df.info() # 完整信息: 行数、类型、空值情况

# 取列 
skill = df['技能']
subdf = df[['技能', '技能等级']] # 取出多列, 双层列表

# 筛选 + 模糊匹配
# 包含字符串模糊匹配, na=False 空值直接跳过不报错
python_jobs = df[
    df['技能要求'].str.contains('Python', na=False)
]

# 正则提取数字薪资
high_pay = df[
    pd.to_numeric(
        df['薪资待遇'].str.extract(r'(\d+)', expand=False),
        errors='coerce' # 转失败的变成 NaN
    ) > 20
]

# 多条件: &且 |或 ~非
condition1 = df['技能要求'].str.contains('Python', na=False)
condition2 = df['技能要求'].str.contains('Flask', na=False)
result = df[condition1 & condition2]

# 去重 
# 按【岗位名称】单列去重, 保留第一次出现
df_unique = df.drop_duplicates(subset=['岗位名称'])
# 多列联合去重: 岗位名称 + 公司同时一样才算重复
df_unique = df.drop_duplicates(subset=['岗位名称', '公司'])
print(f"去重前: {len(df)} 条")
print(f"去重后: {len(df_unique)} 条")

# 新建 DataFrame (造测试数据)
df = pd.DataFrame(
    {
        '列名1': ['行1', '行2', '行3', '行4', '行5', '行6'],
        '列名2': pd.date_range('20260619', periods=6, freq='D')
    },
    columns=['列名1', '列名2']
)

# 空值处理 
print(df.isnull().sum()) # 统计每一列空值数量

df_clean = df.dropna(subset=['技能要求']) # 指定列有空就删掉该行
df_clean = df.dropna() # 任意一列有空值,直接删除整行
df['公司'] = df['公司'].fillna('未知') # 空值填充固定文本

# apply自定义函数处理列 
def extract_first_skill(skill_str):
    if pd.isna(skill_str):
        return '未知'
    return skill_str.split(',')[0]

df['核心技能'] = df['技能要求'].apply(extract_first_skill)

# 分组统计 
avg_salary = df.groupby('岗位名称')['薪资'].mean() # 分组求平均薪资
job_count = df['岗位名称'].value_counts() # 统计每个岗位出现次数

# 拼接、合并表 
# 纵向拼接 (叠加行, 适合同源csv)
boss_df = pd.read_csv('boss.csv')
zhilian_df = pd.read_csv('zhilian.csv')
liepin_df = pd.read_csv('liepin.csv')
all_jobs = pd.concat([boss_df, zhilian_df, liepin_df], ignore_index=True)
print(f"总共有 {len(all_jobs)} 条数据")

# 表连接 (类似SQL join) 
industry_df = pd.read_csv('岗位分类.csv')
merged = pd.merge(
    all_jobs,
    industry_df,
    on='岗位名称', # 匹配键
    how='left' # 左连接:保留左表全部数据
)

# 导出 清洗完输出三种格式
# 导出csv
df_clean.to_csv('清洗后.csv', index=False, encoding="utf-8-sig")

# 导出excel
df_clean.to_excel('清洗后.xlsx', index=False)

# 导出json（最常用records格式）
df_clean.to_json(
    '清洗后.json',
    orient='records',
    force_ascii=False,
    indent=4
)
