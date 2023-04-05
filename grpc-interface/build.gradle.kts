import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
	id("java")
	id("idea")
	id("com.google.protobuf") version "0.8.19"
}

group = "edu.satisf"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	implementation("com.google.protobuf:protobuf-kotlin:3.21.2")
	implementation("io.grpc:grpc-kotlin-stub:1.3.0")
	implementation("io.grpc:grpc-protobuf:1.51.0")

	if (JavaVersion.current().isJava9Compatible()) {
		// Workaround for @javax.annotation.Generated
		// see: https://github.com/grpc/grpc-java/issues/3633
		implementation("javax.annotation:javax.annotation-api:1.3.1")
	}

	// Extra proto source files besides the ones residing under
	// "src/main".
//	protobuf(files("lib/protos.tar.gz"))
//	protobuf(files("ext/"))

}

protobuf {
	protoc {
		// The artifact spec for the Protobuf Compiler
		artifact = "com.google.protobuf:protoc:3.21.2"
	}
	plugins {
		// Optional: an artifact spec for a protoc plugin, with "grpc" as
		// the identifier, which can be referred to in the "plugins"
		// container of the "generateProtoTasks" closure.
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:1.51.0"
		}
		id("grpckt") {
			artifact = "io.grpc:protoc-gen-grpc-kotlin:1.3.0:jdk8@jar"
		}
	}
	generateProtoTasks {
		ofSourceSet("main").forEach {
			it.plugins {
				// Apply the "grpc" plugin whose spec is defined above, without
				// options. Note the braces cannot be omitted, otherwise the
				// plugin will not be added. This is because of the implicit way
				// NamedDomainObjectContainer binds the methods.
//				id("grpc") { }
				id("grpc")
				id("grpckt")
			}
			it.builtins {
				id("kotlin")
			}
		}
	}
}