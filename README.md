##hibernate-goodies
=================

Collection of API's to support in hibernate development

###Goodie No : 1 SequenceIdGenerator
=================================

This can be used as your hibernate id creation strategy, if you wish to use sequence per table. This will automatically generate db sequence for every entity and will take care of supplying new id. The generated seqence will be either picked from Entity's `@Table` annotation's name value or Entity class name  and affix with `seq_` 

__NOTE__ : Now this won't support when using JOIN table Inheritence strategy

eg:

	@Table(name="my_table")
	public class MyClass { .... }

the generated sequence name will be `seq_my_table`.

####Usage
-----
 Annote your primary key entity field with
  	
  	@Id
  	@GenericGenerator(name="seq_id", strategy="org.java.hibernate.support.SequenceIdGenerator")
  	@GeneratedValue(generator="seq_id")
	
