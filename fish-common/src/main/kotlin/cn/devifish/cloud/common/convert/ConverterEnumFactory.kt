package cn.devifish.cloud.common.convert

import cn.devifish.cloud.common.base.ConvertibleEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.core.convert.converter.ConverterFactory

/**
 * 实现 Spring MVC 转换枚举类型
 * 使用枚举类型需要实现 Converter Enum 接口
 * String getValue() -> Enum
 *
 * @see org.springframework.core.convert.converter.ConverterFactory
 * @author Devifish
 */
@Suppress("UNCHECKED_CAST")
class ConverterEnumFactory : ConverterFactory<String, ConvertibleEnum<*>> {

    private val convertMap: MutableMap<String, Converter<String, ConvertibleEnum<*>>> = HashMap()

    /**
     * 获取 对应枚举类型的 转换器
     * @param clazz 枚举 Class对象
     * @param <E> 枚举
     * @return 转换器
     */
    override fun <E : ConvertibleEnum<*>> getConverter(clazz: Class<E>): Converter<String, E> {
        val name = clazz.name
        if (!convertMap.containsKey(name)) {
            val map = clazz.enumConstants.associate { "${it.getValue()}" to it }
            convertMap[name] = Converter { key -> map[key] }
        }
        return convertMap[name] as Converter<String, E>
    }

}