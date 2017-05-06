SHELL := /bin/bash

help:
	@echo "Please use \`make <target>' where <target> is one of"
	@echo "  all               to update, generate and test this SDK"
	@echo "  test              to run service test"
	@echo "  unit              to run all sort of unit tests except runtime"
	@echo "  update            to update git submodules"
	@echo "  generate          to generate service code"

all: update generate unit

test:
	@echo "run service test"
	@if [[ ! -f "$$(which javac)" ]]; then \
		echo "ERROR: Command \"javac\" not found."; \
	fi
	rm -f build/libs/qingstor*test*.jar
	./gradlew buildTestJar
	rm -fr tests/jars
	mkdir tests/jars
	cp build/libs/qingstor*test*.jar tests/jars/
	pushd "tests";\
	javac -cp "./jars/*:." scenario_impl/*.java;\
	java -cp "./jars/*:." cucumber.api.cli.Main -g scenario_impl features;\
	popd
	rm -fr tests/jars
	rm -f tests/scenario_impl/*.class
	
	@echo "ok"

generate:
	@if [[ ! -f "$$(which snips)" ]]; then \
		echo "ERROR: Command \"snips\" not found."; \
	fi
	snips \
		--service=qingstor --service-api-version=latest \
		--spec="./specs" --template="./template" --output="./src/main/java/com/qingstor/sdk/service"
	rm ./src/main/java/com/qingstor/sdk/service/Object.java
	./gradlew formatGenerateCode
	@echo "ok"

update:
	git submodule update --init
	@echo "ok"

unit:
	@echo "run unit test"
	./gradlew test
	@echo "ok"
	
buildJar:
	@echo "run build jar"
	./gradlew buildJar
	./gradlew buildIncludeDependentJar
	@echo "ok"

