### 一、安装


### 二、概念

> elasticSearch是面向文档、关系行数据库和elasticSearch客观的对比

| Relational DB | ElasticSearch |
| ----- | ----- | 
| 数据库（database） | 索引（indices） |
| 表（tables） | types |
| 行（rows） | documents |
| 字段（columns） | fields |

> elasticSearch（集群）中可以包含多个索引（数据库），每个索引中可以包含多个类型（表），每个类型下又包含多个文档（行），每个文档中又包含多个字段（列）

物理设计：
> elasticSearch在后台把每个索引划分成多个分片，每个分片可以在集群中的不同服务器间迁移

逻辑设计：
> 一个索引类型中，包含多个文档

> - ik_smart最少切分
> - ik_max_word最细粒度划分，穷尽词库的可能

### 三、Rest风格说明

| method | url地址 | 描述 |
| ----- | ------ | ----- |
| PUT | localhost:9200/索引名称/类型名称/文档id | 创建文档（指定文档id） |
| POST | localhost:9200/索引名称/类型名称 | 创建文档（随机文档id） |
| POST | localhost:9200/索引名称/类型名称/文档id/_update | 修改文档 |
| DELETE | localhost:9200/索引名称/类型名称/文档id | 删除文档 |
| GET | localhost:9200/索引名称/类型名称/文档id | 查询文档通过文档id |
| POST | localhost:9200/索引名称/类型名称/_search | 查询所有数据 |

### 四、关于文档的基本操作

1.简单查询（参考Rest风格里面的基础查询）

2.复杂查询（排序、分页、高亮、模糊查询、精确查询）
```text
GET test/user/_search
{
    "query": {
        "match": {
            "name": "test" // 查询test索引下user类型的name为test的信息，模糊查询，可通过hit -> score判断哪条结果更加符合
        }
    },
    "_source": ["name", "desc"], // 结果过滤，只需要查询结果展示name和desc字段
    "sort": [ // sort根据age降序排序
        {
            "age": {
                "order": "desc"
            }
        }
    ],
    "from": 0, // from 和size用于排序，从0开始，显示当页面的10条数据
    "size": 10 
}
```

基础条件解释：
> - must：where name = 'test' and age = 3
> - should： where name = 'test' or age = 3
> - must_not： where age <> 3
```text
GET test/user/_search
{
    "query": {
        "bool": {
            "must": [
                {
                    "match": {
                        "name": "test"
                    }
                },
                {
                    "match": {
                        "age": 3
                    }
                }
            ]
        }
    }
}
// 多条件精确查询，name必须包含test和age必须为3
```

过滤器：
> filter
```text
GET test/user/_search
{
    "query": {
        "bool": {
            "must": [
                {
                    "match": {
                        "name": "test"
                    }
                }
            ],
            "filter": {
                "range": {
                    "age": {
                        "lt": 10
                    }
                }
            }
        }
    }
}
// 查询必须包含name为test的数据，且过滤掉age小于10
// gt 大于
// gte 大于等于
// lt 小于
// lte 小于等于
```

> - 精确查询term，是通过倒排索引指定的词条进行精确查找的
> - match，会使用分词器解析（先分析文档，然后再通过分析的文档进行查询）
> - type为text：会把条件当作一个分词器去解析搜索
> - type为keyword：不会被分词器解析，会把条件当作一个整体搜索

高亮查询：
```text
GET test/user/_search
{
    "query": {
        "match": {
            "name": "test"
        }
    },
    "highlight": {
        "pre_tags": "<p class='key' style='color:red'>", // 自定义高亮显示
        "post_tags": "</p>",
        "field": {
            "name": {} // 搜索出来的结果高亮显示
        }
    }
}
```