package quarkus.genres;

import io.quarkus.hibernate.orm.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jdk.jfr.Registered;

@RegisterForReflection
public record GenreResponseDto(Long id, @ProjectedFieldName("name") String title) {
}
