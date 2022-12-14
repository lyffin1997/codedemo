package com.lyffin.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public final class RedisUtil {

    /**
     * 注入RedisTemplate
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间（秒）
     * @return
     * @author twy
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key获取过期时间
     *
     * @param key
     * @return 返回时间（秒），返回0代表永久有效
     * @author twy
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return 存在true  不存在false
     * @author twy
     */
    public boolean hasKey(String key) {
        try {
            redisTemplate.hasKey(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个或者多个值（相当于数组）
     * @author twy
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 0) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    /******************************************************String*******************************************************/

    /**
     * 普通缓存获取（String）
     *
     * @param key
     * @return
     * @author twy
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key
     * @param value
     * @return
     * @author twy
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 普通缓存放入并设置过期时间(秒)
     *
     * @param key
     * @param value
     * @return
     * @author twy
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key
     * @param delta 增加的数值(大于0)
     * @return
     * @author twy
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key
     * @param delta 减少的数值(大于0)
     * @return
     * @author twy
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /******************************************************Map*******************************************************/

    /**
     * 根据key和vale获取对应的值
     *
     * @param key
     * @param item
     * @return
     * @author twy
     */
    public Object hGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }


    /**
     * 获取hash的key对应的所有值
     *
     * @param key
     * @return
     * @author twy
     */
    public Map<Object, Object> hMget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet 以map格式存入
     *
     * @param key 键
     * @param map 对应多个键值
     * @return
     * @author twy
     */
    public boolean hMset(String key, Map<Object, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 以map格式存入（并设置过期时间）
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间（秒）
     * @return true 成功  false 失败
     * @author twy
     */
    public boolean hMset(String key, Map<Object, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 向一张hash表中放入数据,如果不存在则创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return
     * @author twy
     */
    public boolean hSet(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 向一张hash表中放入数据,如果不存在则创建 (并设置过期时间)
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  过期时间,如果已存在time,则会替换掉原有的时间
     * @return
     * @author twy
     */
    public boolean hSet(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key
     * @param item 项  可以有多个但是不能为null
     * @author twy
     */
    public void hDel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash中是否存在该项的值
     *
     * @param key
     * @param item
     * @return
     * @author twy
     */
    public boolean hHaskey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在，就会创建一个,并把新增后的值返回
     *
     * @param key   键
     * @param item  项
     * @param detal 递增的值（大于0）
     * @return
     * @author twy
     */
    public double hIncr(String key, String item, double detal) {
        return redisTemplate.opsForHash().increment(key, item, detal);
    }

    /**
     * hash递减
     *
     * @param key   键
     * @param item  项
     * @param detal 递减的值（小于0）
     * @return
     * @author twy
     */
    public double hDecr(String key, String item, double detal) {
        return redisTemplate.opsForHash().increment(key, item, -detal);
    }

    /******************************************************Set*******************************************************/

    /**
     * 根据key获取set中所有的值
     *
     * @param key
     * @return
     * @author twy
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据key和value查询在set中是否存在
     *
     * @param key
     * @param value
     * @return
     * @author twy
     */
    public boolean sHaskey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值（可以是多个）
     * @return 返回成功的个数
     * @author twy
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将数据放入set缓存（并且增加过期时间）
     *
     * @param key    键
     * @param values 值（可以是多个）
     * @param time   过期时间
     * @return 返回成功的个数
     * @author twy
     */
    public long sSet(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     * @author twy
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 在set缓存中移出值为values的key
     *
     * @param key
     * @param values 值可以为多个
     * @return
     * @author twy
     */
    public long setRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /******************************************************List*******************************************************/

    /**
     * 获取lis缓存的内容
     *
     * @param key
     * @param start 开始
     * @param end   结束    （0 -1）-->获取所有值
     * @return
     * @author twy
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存d 长度
     *
     * @param key
     * @return
     * @author twy
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引获取list中的值
     *
     * @param key
     * @param index 索引 0:表头 1:第二个元素 -1:表尾 -2:倒数第二个元素
     * @return
     * @author twy
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * list缓存放入值
     *
     * @param key
     * @param value
     * @return
     * @author twy
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * list缓存放入值,增加过期时间
     *
     * @param key
     * @param value
     * @param time
     * @return
     * @author twy
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * list缓存放入值，放入list
     *
     * @param key
     * @param value
     * @return
     * @author twy
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * list缓存放入值，放入list，增加过期时间
     *
     * @param key
     * @param value
     * @return
     * @author twy
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移出count个值为value的key
     *
     * @param key
     * @param count 移出多少个
     * @param value
     * @return
     * @author twy
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //先写到这里啦.....按照这种样式写就完事了
}