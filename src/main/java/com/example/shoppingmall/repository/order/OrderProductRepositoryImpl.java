package com.example.shoppingmall.repository.order;

import com.example.shoppingmall.data.dto.queryselect.ReadOrderQuery;
import com.example.shoppingmall.data.entity.OrderProduct;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.shoppingmall.data.entity.QOrderProduct.orderProduct;

@Repository
@RequiredArgsConstructor
public class OrderProductRepositoryImpl implements OrderProductRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public BooleanExpression eqOrderIDList(List<Long> orderIDList){
        if (orderIDList == null){
            return null;
        }
        return orderProduct.id.in(orderIDList);
    }

    @Override
    public void deleteOrderIDList(List<Long> orderIDList){
        BooleanExpression status = eqOrderIDList(orderIDList);

        if (status == null) {
            return;
        }

        queryFactory.delete(orderProduct)
                .where(status)
                .execute();
    }

    @Override
    public List<ReadOrderQuery> findResponseOrder(List<OrderProduct> orderProductList) {
        // DTO 로 조회 && IN 으로 다수의 엔티티를 한번에 조회
        BooleanExpression status = eqOrderIDList(orderProductList.stream().map(OrderProduct::getId).toList());

        if(status == null){
            return null;
        }

        return queryFactory.select(
                Projections.fields(ReadOrderQuery.class,
                        orderProduct.count,
                        orderProduct.product.price,
                        orderProduct.size,
                        orderProduct.product.imgKey,
                        new CaseBuilder()
                                .when(orderProduct.product.stock.gt(0))
                                .then(true)
                                .otherwise(false).as("stock_zero")
                ))
                .from(orderProduct)
                .where(status)
                .fetch();
    }
}
