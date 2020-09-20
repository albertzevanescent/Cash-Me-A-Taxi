JFLAGS = -g
JCLASS = -cp ./src:.:$(CLASSPATH):./../../../eclipse/junit-4.5.jar
JC = javac
JVM = java
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $(JCLASS) $*.java

CLASSES = \
	src/Equality.java \
	src/Seq.java \
	src/Node.java \
	src/Edge.java \
	src/Graph.java \
	src/GraphSearch.java \
	src/GraphSort.java \
	src/GraphPath.java \
	src/Parser.java \
	src/FileController.java \
	src/CashMeATaxi.java \

MAIN = AllTests

default: classes

classes: $(CLASSES:.java=.class)

doc:
	doxygen doxConfig
	cd latex && $(MAKE)

test: src/$(MAIN).class
	$(JVM) $(JCLASS) org.junit.runner.JUnitCore $(MAIN)

cmat: src/CashMeATaxi.java
	$(JC) $(JCLASS) $(JFLAGS) src/CashMeATaxi.java
	$(JVM) $(JCLASS) src/CashMeATaxi
clean:
	rm -rf html
	rm -rf latex
	cd src
	rm **/*.class
