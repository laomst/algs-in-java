package site.laomst.learn.dsag.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 某个方法是算法的最佳实现
 */
@Target(ElementType.METHOD)
public @interface Best {
}
