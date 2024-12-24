SHELL := /bin/bash

.PHONY: help all test unit build update generate

help:
	@echo "Please use \`make <target>' where <target> is one of"
	@echo "  all               to update, generate and test this SDK"
	@echo "  test              to run service test"
	@echo "  unit              to run all sort of unit tests except runtime"
	@echo "  build             to build jar"
	@echo "  update            to update git submodules"
	@echo "  generate          to generate service code"

all: update generate unit build

test:
	@echo "run service test"
	mvn integration-test
	@echo "ok"

generate:
	@if [[ ! -f "$$(which snips)" ]]; then \
		echo "ERROR: Command \"snips\" not found."; \
	fi
	snips \
		-f=./specs/qingstor/2016-01-06/swagger/api_v2.0.json -t=./template -o=./src/main/java/com/qingstor/sdk/service
	rm ./src/main/java/com/qingstor/sdk/service/Object.java
	mvn spotless:apply
	@echo "ok"

update:
	git submodule update --init --recursive
	@echo "ok"

unit:
	@echo "run unit test"
	mvn test
	@echo "ok"

build:
	@echo "run build jar(jar, source, javadoc)"
	mvn package -Prelease
	@echo "ok"
