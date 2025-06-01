-- Create new person
insert into persons
    (ID, NAME, FAMILY, USERNAME, PASSWORD, BIRTH_DATE, ROLE, ALGORITHM_SKILL, JAVA_SKILL, GENDER)
values
    (2, 'ali', 'alipour', 'ali', 'ali123', null, null, 0,1, 'male');


-- Read person(s)
select
    ID, NAME, FAMILY, USERNAME, PASSWORD, BIRTH_DATE, ROLE, ALGORITHM_SKILL, JAVA_SKILL, GENDER
from
    persons
where
    name='ali' and ALGORITHM_SKILL=0;

-- Update person(s)
update persons set name='alireza', password='aliali112233', JAVA_SKILL=0 where id=2;


-- Delete person(s)
delete from persons where id=2;



-- insert/update/delete
commit;