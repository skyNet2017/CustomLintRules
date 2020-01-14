package com.sollian.lintjar;


import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.PsiMethod;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UCallExpression;

import java.util.Arrays;
import java.util.List;

public class ToastDetector extends Detector implements Detector.UastScanner {

    public static final Issue ISSUE = Issue.create(
            "ToastDetector",
            "避免直接调用Toast",
            "请勿直接调用Toast，应该使用统一工具类AkuToast",
            Category.LINT, 5, Severity.ERROR,
            new Implementation(ToastDetector.class, Scope.JAVA_FILE_SCOPE));

    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("makeText");
    }

    @Override
    public void visitMethodCall(@NotNull JavaContext context,
                                @NotNull UCallExpression node,
                                @NotNull PsiMethod method) {
        if (context.getEvaluator().isMemberInClass(method, "android.widget.Toast")) {
            context.report(ISSUE, node, context.getLocation(node), "避免直接调用Toast");
        }
    }
}