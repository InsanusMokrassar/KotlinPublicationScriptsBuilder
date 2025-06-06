package dev.inmo.kmppscriptbuilder.core.export

import dev.inmo.kmppscriptbuilder.core.models.MavenConfig

const val generateCentralSonatypeUploadingPartImports = """import java.nio.charset.StandardCharsets
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse"""
const val generateCentralSonatypeUploadingPart = """// This script work based on https://ossrh-staging-api.central.sonatype.com/swagger-ui/#/default/manual_upload_repository
// and getting available open repos and just uploading them
if ((project.hasProperty('SONATYPE_USER') || System.getenv('SONATYPE_USER') != null) && (project.hasProperty('SONATYPE_PASSWORD') || System.getenv('SONATYPE_PASSWORD') != null)) {
    def taskName = "uploadSonatypePublication"
    if (rootProject.tasks.names.contains(taskName) == false) {
        rootProject.tasks.register(taskName) {
            doLast {
                def username = project.hasProperty('SONATYPE_USER') ? project.property('SONATYPE_USER') : System.getenv('SONATYPE_USER')
                def password = project.hasProperty('SONATYPE_PASSWORD') ? project.property('SONATYPE_PASSWORD') : System.getenv('SONATYPE_PASSWORD')
                def bearer = Base64.getEncoder().encodeToString("${"$"}username:${"$"}password".getBytes(StandardCharsets.UTF_8))
    
                def client = HttpClient.newHttpClient()
                def request = HttpRequest.newBuilder()
                        .uri(URI.create("https://ossrh-staging-api.central.sonatype.com/manual/search/repositories?state=open"))
                        .GET()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer ${"$"}bearer")
                        .build()
    
                def response = client.send(request, HttpResponse.BodyHandlers.ofString())
                def keys = new ArrayList<String>()
                response.body().findAll("\"key\"[\\s]*:[\\s]*\"[^\"]+\"").forEach {
                    def key = it.find("[^\"]+\"\$").find("[^\"]+")
                    keys.add(key)
                }
                keys.forEach {
                    println("Start uploading ${"$"}it")
                    def uploadRequest = HttpRequest.newBuilder()
                            .uri(URI.create("https://ossrh-staging-api.central.sonatype.com/manual/upload/repository/${"$"}it?publishing_type=user_managed"))
                            .POST(HttpRequest.BodyPublishers.ofString(""))
                            .header("Content-Type", "application/json")
                            .header("Authorization", "Bearer ${"$"}bearer")
                            .build()
                    def uploadResponse = client.send(uploadRequest, HttpResponse.BodyHandlers.ofString())
                    if (uploadResponse.statusCode() != 200) {
                        throw new IllegalStateException("Faced error of uploading for repo with key ${"$"}it. Response: ${"$"}uploadResponse")
                    }
                }
            }
        }
    }
}"""
