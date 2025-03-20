clean:
	./gradlew clean

build: clean
	./gradlew build

native: build
	./gradlew nativeCompile