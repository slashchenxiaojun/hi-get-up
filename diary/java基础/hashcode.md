#hashcode
通常来说比较对象需要使用覆写Object.equals()，如果只是想单纯的比较2个对象是否相同只需要覆写Object.equals()就足够了，但是如果你的对象需要在Set集合类中使用的话，就必须特别注意一下Object.hashCode()，它在Hash集合中用于判断2个对象是否相等

##hashMap

###内部结构