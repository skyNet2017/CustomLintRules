package com.sollian.lintjar;

import com.android.tools.lint.client.api.JavaEvaluator;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UReferenceExpression;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

public class JavaBeanIdDetector extends Detector implements Detector.UastScanner {

    public static final Issue ISSUE = Issue.create(
            "JavaBeanIdInt",
            "JavaBean里,后台返回的id不得为int,应为long",
            "id为int,后台返回大数时,会json解析异常",
            Category.LINT, 5, Severity.ERROR,
            new Implementation(JavaBeanIdDetector.class, Scope.JAVA_FILE_SCOPE));


    @Nullable
    @Override
    public List<String> getApplicableReferenceNames() {
        return Arrays.asList("id","uid");
    }

    @Override
    public void visitReference(@NotNull JavaContext context, @NotNull UReferenceExpression reference,
                               @NotNull PsiElement referenced) {
        //super.visitReference(context, reference, referenced);
        //if (context.getUastParser()) {
           // context.report(ISSUE, node, context.getLocation(node), "避免调用android.util.Log");
        //}
        if(reference.getResolvedName().contains("id") || reference.getResolvedName().contains("Id")){
            context.report(ISSUE, reference, context.getLocation(reference), "id避免int,要用long");
        }
    }






}
