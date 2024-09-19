/* Lấy tất cả các sản phẩm hiển thị cho người dùng*/
SELECT p.*, pp.*,
       COALESCE(
               CASE
                   WHEN pp.expired_date >= CURRENT_DATE AND pp.issue_date > CURRENT_DATE THEN p.price
                   WHEN pp.expired_date >= CURRENT_DATE AND pp.issue_date <= CURRENT_DATE THEN pp.discounted_amount
                   ELSE NULL
                END,
               p.price
       ) AS price
FROM t_products p left JOIN t_product_prices pp
                            ON pp.product_id = p.product_id
WHERE (pp.discounted_price =
       (SELECT MAX(pp2.discounted_price) FROM t_product_prices pp2
        WHERE pp2.product_id = p.product_id)
    OR pp.discounted_price ISNULL)
  AND p.product_status = 'ACTIVE';


/* Lấy tất cả các sản phẩm đang khuyến mãi hiển thị cho người dùng*/
SELECT p.*, pp.* FROM t_products p left JOIN t_product_prices pp
        ON pp.product_id = p.product_id
        AND pp.issue_date <= CURRENT_DATE
        AND pp.expired_date >= CURRENT_DATE
WHERE (pp.discounted_price =
       (SELECT MAX(pp2.discounted_price) FROM t_product_prices pp2
        WHERE pp2.product_id = p.product_id) )
  AND p.product_status = 'ACTIVE';

/* Lấy tất cả các sản phẩm sắp khuyến mãi hiển thị cho người dùng*/
SELECT p.*, pp.* FROM t_products p left JOIN t_product_prices pp
        ON pp.product_id = p.product_id
        AND pp.issue_date > CURRENT_DATE
        AND pp.expired_date >= CURRENT_DATE
WHERE (pp.discounted_price =
       (SELECT MAX(pp2.discounted_price) FROM t_product_prices pp2
        WHERE pp2.product_id = p.product_id) )
  AND p.product_status = 'ACTIVE'
;
