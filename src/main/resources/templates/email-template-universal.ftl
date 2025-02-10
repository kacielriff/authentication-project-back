<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Auth Project - ${subject}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #FFFFFF;
            color: #040308;
            margin: 0;
            padding: 20px;
            line-height: 1.5;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            background: #FFFFFF;
            padding: 20px;
        }
        h1 {
            font-size: 24px;
            text-align: center;
            margin: 20px auto 0;
        }
        p {
            font-size: 16px;
            margin-bottom: 20px;
            text-align: center;
        }
        .button {
            display: inline-block;
            background-color: #312ECB;
            color: #FFFFFF;
            text-decoration: none;
            font-size: 16px;
            font-weight: bold;
            padding: 12px 24px;
            border-radius: 6px;
            text-align: center;
            transition: all 0.3s ease-in-out;
        }
        .button:hover {
            background-color: #1F1AA6;
        }
        .button:visited {
            color: #FFFFFF;
        }
        .text-link:visited {
            color: #0078ff;
        }
        .footer {
            font-size: 12px;
            text-align: center;
            margin-top: 20px;
            color: #666;
        }
    </style>
</head>
    <body>
        <div class="container">
            <h1>${subject}</h1>
            <hr style="width: 25%; margin: 4px auto 24px;">
            <p>${content}</p>
            <p style="text-align: center; color: #FFFFFF;">
                <a href="${url}" class="button" aria-label="${buttonLabel}" style="color: #FFFFFF;">${buttonLabel}</a>
            </p>
            <p>Se o botão acima não funcionar, tente o link abaixo no seu navegador:</p>
            <p style="color: #0078ff;"><a href="${url}" class="text-link" aria-label="${buttonLabel}">${url}</a></p>
            <#if expirationTime?has_content>
                <p class="footer" style="font-size: 14px;">Este link irá expirar em ${expirationTime}. Após esse período, será necessário solicitar um novo link.</p>
            </#if>
            <hr style="width: 75%; margin: 0 auto;">
            <p class="footer">Se você não solicitou este e-mail, ignore-o com segurança.</p>
        </div>
    </body>
</html>
