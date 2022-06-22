import jieba.analyse
import pandas as pd
from textrank4zh import TextRank4Keyword, TextRank4Sentence

df = pd.read_csv(r"E:\clear_data\weblog\20060801.csv",error_bad_lines=False)
df_list = df["word"].tolist()
data = ','.join('%s' %a for a in df_list)
# 创建分词类的实例
tr4w = TextRank4Keyword()
# 对文本进行分析，设定窗口大小为2，并将英文单词小写
tr4w.analyze(text=data, lower=True, window=2)
print('关键词为：')
# 从关键词列表中获取前30个关键词
for item in tr4w.get_keywords(num=30, word_min_len=1):
    # 打印每个关键词的内容及关键词的权重
    print(item.word, item.weight)

