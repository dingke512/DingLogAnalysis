import re
from pprint import pprint
import jieba
import pandas as pd
import pyLDAvis.gensim
from gensim import corpora, models
import warnings

warnings.filterwarnings('ignore')
# 文件路径
path = "E:\\clear_data\\weblog\\"
# 文件名
date = "20060821"

filepath = path + date + '.csv'
df = pd.read_csv(filepath,error_bad_lines=False)
print(df.count())
words = df["word"].tolist()

stopwords = pd.read_csv('stopwords.txt', encoding='gbk', names=['stopwords'], index_col=False)
stop_list = stopwords['stopwords'].tolist()
# 提取句子中中英文数字
r1 = re.compile('[^\u4e00-\u9fa5\u0030-\u0039\u0041-\u005a\u0061-\u007a]')
text = []
# 停用词过滤
for i in range(len(words)):
    clear = re.sub(r1,"",str(words[i]))
    cut_list = jieba.lcut(clear,cut_all=False)
    item = [j for j in cut_list if j not in stop_list]
    text.append(item)
# 构建词和id 的 映射词典
dictionary = corpora.Dictionary(text)
dictionary.save("../lda/"+date+ "/corpora.dict")
# 将每个句子样本表示成向量
doc_term_matrix = [dictionary.doc2bow(doc) for doc in text]
Lda = models.ldamodel.LdaModel
model = Lda(corpus=doc_term_matrix, num_topics=3, id2word=dictionary)  # passes : epochs。 iterations: 在每个文档上多久重复一次特定的循环。chunksize: 参数决定同时训练的文档数
pprint(model.print_topics(num_topics=3, num_words=30))
model.save("../lda/"+date+'/scenic_lda.model')
vis = pyLDAvis.gensim.prepare(model, doc_term_matrix, dictionary)
pyLDAvis.save_html(vis,'../lda/'+date+'/'+date+'.html')






