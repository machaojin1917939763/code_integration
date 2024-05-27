package cn.machaojin.service.jgit;

import cn.machaojin.domain.Developer;
import cn.machaojin.service.DeveloperService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.util.io.NullOutputStream;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.machaojin.constants.UserConstant.AVATAR;

/**
 * @author Ma Chaojin
 * @since 2024-05-08 17:46
 */
@Data
@Slf4j
@Component
@RequiredArgsConstructor
public class JGitService {
    private final DeveloperService developerService;

    public void analyzeGit(String path) throws Exception {
        File repoDir = new File(path + "\\.git");
        log.info("repoDir:{}", repoDir.getName());
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder.setGitDir(repoDir)
                .readEnvironment()
                .findGitDir()
                .build();
        Git git = new Git(repository);
        Iterable<RevCommit> commits = git.log().all().call();
        Map<String, Integer> developerContributions = new HashMap<>();

        for (RevCommit commit : commits) {
            if (commit.getParentCount() > 0) {
                RevCommit parent = commit.getParent(0);
                DiffFormatter df = new DiffFormatter(NullOutputStream.INSTANCE);
                df.setRepository(git.getRepository());
                df.setDetectRenames(true);

                List<DiffEntry> diffs = df.scan(parent.getTree(), commit.getTree());

                int linesAdded = 0;
                int linesDeleted = 0;
                for (DiffEntry diff : diffs) {
                    FileHeader fileDiffHeader = df.toFileHeader(diff);
                    for (Edit edit : fileDiffHeader.toEditList()) {
                        linesAdded += edit.getEndB() - edit.getBeginB();
                        linesDeleted += edit.getEndA() - edit.getBeginA();
                    }
                }

                String developerName = commit.getAuthorIdent().getName();
                String emailAddress = commit.getAuthorIdent().getEmailAddress();
                developerContributions.merge(developerName + ":" + emailAddress, linesAdded - linesDeleted, Integer::sum);
                df.close();
            }
        }
        developerContributions.forEach((developer, lines) -> {
            String[] split = developer.split(":");
            String name = (split[0] == null || split[0].isEmpty()) ? "" : split[0];
            String email = (split[1] == null || split[1].isEmpty()) ? "" : split[1];
            Developer serviceOne = developerService.getOne(new LambdaQueryWrapper<>(Developer.class).eq(Developer::getName, name).eq(Developer::getEmail, email));
            if (serviceOne == null) {
                developerService.save(Developer.builder().avatar(AVATAR).email(email).name(name).score(lines * 100).password("123456").build());
            }else {
                serviceOne.setScore(lines * 100);
                developerService.updateById(serviceOne);
            }
        });
    }
}
