hibernate-goodies
=================

Collection of API's to support in hibernate development

Goodie No : 1 SequenceIdGenerator
=================================

This can be used as your hibernate id creation strategy, if you wish to use sequence per table. This will automatically generate db sequence for every entity and will take care of supplying new id.

Usage
-----
 Annote your primary key entity field with
  
  @Id
  @GenericGenerator(name="seq_id", strategy="org.java.hibernate.support.SequenceIdGenerator")
  @GeneratedValue(generator="seq_id")
	
