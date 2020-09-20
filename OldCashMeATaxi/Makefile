# by Areeba Aziz

.PHONY: report

default: report

doc:
	doxygen doxConfig
	cd latex && $(MAKE)

report:
	cd report && pdflatex report.tex

clean:
	rm -rf html
	rm -rf latex
